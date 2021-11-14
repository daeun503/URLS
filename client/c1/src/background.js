chrome.contextMenus.create({
  title: 'Urls에서 관리하기',
  onclick: () => {
    chrome.tabs
      .executeScript({file: 'contentScripts/highlight.js'})
      .then(result => result)
      .catch(error => error);
  },
  contexts: ['selection'],
});

chrome.contextMenus.create({
  title: 'Toggle Cursor',
  onclick: () => {
    chrome.tabs
      .executeScript({
        file: 'contentScripts/toggleHighlighterCursor.js',
      })
      .then(result => result)
      .catch(error => error);
  },
});

chrome.storage.sync.get('color', values => {
  const color = values.color ? values.color : 'yellow';
  chrome.contextMenus.update(color, {checked: true});
});

// 백그라운드 로직 처리 => 비동기적인 상황(promise)
chrome.runtime.onMessage.addListener((request, _sender, _sendResponse) => {
  if (request.action && request.action === 'highlight') {
    chrome.tabs
      .executeScript({file: 'contentScripts/highlight.js'})
      .then(result => result);
  } else if (request.action && request.action === 'serverCheck') {
    const baseURL = 'http://k5b201.p.ssafy.io:4000';
    const config = {
      method: 'GET',
      headers: {'Content-Type': 'application/json;'},
    };
    fetch(`${baseURL}/user`, config)
      .then(result => {
        const status = result.status === 200 ? 'Live' : 'Dead';
        alert(`서버 상태는 ${status}`);
      })
      .catch(error => {
        alert(error);
      });
  }
});
