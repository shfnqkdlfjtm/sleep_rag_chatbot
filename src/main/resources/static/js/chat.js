// src/main/resources/static/js/chat.js
const userId = localStorage.getItem("userId") || (Math.random().toString(36).slice(2));
localStorage.setItem("userId", userId);

const els = {
  personas: document.getElementById("personaSelect"),
  cbtiWeek: document.getElementById("cbtiWeek"),
  cbtiGuide: document.getElementById("cbtiGuide"),
  tts: document.getElementById("ttsToggle"),
  stats: document.getElementById("stats"),
  refs: document.getElementById("references"),
  refsEmpty: document.getElementById("referencesEmpty"),
  messages: document.getElementById("messages"),
  input: document.getElementById("userInput"),
  send: document.getElementById("sendBtn"),
  rec: document.getElementById("recBtn"),
  stop: document.getElementById("stopBtn"),
};

function now(){
  const d=new Date(); return d.toLocaleTimeString('ko-KR',{hour:'2-digit',minute:'2-digit',hour12:true});
}
function escapeHtml(s){ return s.replace(/[&<>"']/g, m=>({ '&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#039;' }[m])); }

function addUserMessage(content, timestamp){
  const row = document.createElement("div"); row.className = "msg-row msg-user";
  const bubble = document.createElement("div"); bubble.className = "msg-bubble bubble-user";
  bubble.innerHTML = `<div>${escapeHtml(content)}</div>
    <div style="display:flex;justify-content:flex-end;color:#333;font-size:.75rem;margin-top:6px;">${timestamp}</div>`;
  row.appendChild(bubble); els.messages.appendChild(row);
  els.messages.scrollTop = els.messages.scrollHeight;
}
function addBotMessage(answer, timestamp, references, ttsBase64){
  const row = document.createElement("div"); row.className = "msg-row";
  const bubble = document.createElement("div"); bubble.className = "msg-bubble bubble-bot";
  bubble.innerHTML = `<div style="font-size:1rem;line-height:1.4">${answer}</div>
    <div style="font-size:.75rem;color:#666;margin-top:6px">${timestamp}</div>`;
  if (ttsBase64){
    const audio = document.createElement("audio"); audio.controls = true;
    audio.src = "data:audio/mp3;base64," + ttsBase64; bubble.appendChild(audio);
  }
  row.appendChild(bubble); els.messages.appendChild(row);
  renderReferences(references||[]); els.messages.scrollTop = els.messages.scrollHeight;
}
function renderReferences(refs){
  els.refs.innerHTML = "";
  if (!refs || refs.length === 0){ els.refsEmpty.style.display="block"; return; }
  els.refsEmpty.style.display="none";
  refs.forEach(d=>{
    const card = document.createElement("div"); card.className="ref-card";
    const title = document.createElement("div"); title.className="ref-title";
    title.textContent = `${d.disease||""} ${d.tab?(" - "+d.tab):""}`;
    const content = document.createElement("div"); content.textContent = d.content || "";
    card.appendChild(title); card.appendChild(content); els.refs.appendChild(card);
  });
}

async function loadCbtiGuide(){
  const w = els.cbtiWeek.value;
  if(!w){ els.cbtiGuide.textContent=""; return; }
  const res = await fetch(`/api/cbti/guide/${w}`); const data = await res.json();
  els.cbtiGuide.textContent = data.guide || "";
}
async function loadStats(){
  const res = await fetch(`/api/stats/${userId}`); const s = await res.json();
  els.stats.innerHTML = `총 대화 수: ${s.total||0}`;
}

async function sendText(){
  const q = els.input.value.trim(); if(!q) return;
  const persona = els.personas.value;
  addUserMessage(q, now()); els.input.value="";
  const res = await fetch(`/api/chat/text?userId=${encodeURIComponent(userId)}&persona=${encodeURIComponent(persona)}&cbtiWeek=${els.cbtiWeek.value}&tts=${els.tts.checked}`, {
    method:"POST", headers:{ "Content-Type":"application/json" }, body: JSON.stringify({ query:q })
  });
  const data = await res.json();
  addBotMessage(data.answer, now(), data.references, data.ttsBase64);
  loadStats();
}

let mediaRecorder, chunks=[];
async function startRec(){
  const stream = await navigator.mediaDevices.getUserMedia({audio:true});
  chunks=[]; mediaRecorder = new MediaRecorder(stream);
  mediaRecorder.ondataavailable = e=>chunks.push(e.data);
  mediaRecorder.onstop = async ()=>{
    const blob = new Blob(chunks,{type:"audio/webm"});
    const arr = await blob.arrayBuffer();
    const fd = new FormData(); fd.append("audio", new Blob([arr], {type:"application/octet-stream"}), "rec.webm");
    const persona = els.personas.value;
    const res = await fetch(`/api/chat/voice?userId=${encodeURIComponent(userId)}&persona=${encodeURIComponent(persona)}&cbtiWeek=${els.cbtiWeek.value}&tts=${els.tts.checked}`, {
      method:"POST", body: fd
    });
    const data = await res.json();
    if (data.transcript) addUserMessage(data.transcript, now());
    addBotMessage(data.answer, now(), data.references, data.ttsBase64);
    loadStats();
  };
  mediaRecorder.start();
  els.rec.disabled=true; els.stop.disabled=false;
}
function stopRec(){ if(mediaRecorder){ mediaRecorder.stop(); } els.rec.disabled=false; els.stop.disabled=true; }

window.addEventListener("load", async ()=>{
  await loadCbtiGuide(); await loadStats();
  els.send.addEventListener("click", sendText);
  els.input.addEventListener("keydown", (e)=>{ if(e.key==="Enter") sendText(); });
  els.cbtiWeek.addEventListener("change", loadCbtiGuide);
  els.rec.addEventListener("click", startRec);
  els.stop.addEventListener("click", stopRec);
});
