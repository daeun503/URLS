const STORE_FORMAT_VERSION = chrome.runtime.getManifest().version;
let alternativeUrlIndexOffset = 0;
// Number of elements stored in the alternativeUrl Key. Used to map highlight indices to correct key

function elementFromQuery(storedQuery) {
  const re = />textNode:nth-of-type\(([0-9]+)\)$/iu;
  const result = re.exec(storedQuery);

  if (result) {
    // For text nodes, nth-of-type needs to be handled differently (not a valid CSS selector)
    const textNodeIndex = parseInt(result[1], 10);
    storedQuery = storedQuery.replace(re, '');
    const parent = $(storedQuery)[0];

    if (!parent) return undefined;

    return parent.childNodes[textNodeIndex];
  }

  return $(storedQuery)[0];
}

function getQuery(element) {
  if (element.id) return `#${escapeCSSString(element.id)}`;
  if (element.localName === 'html') return 'html';

  const parent = element.parentNode;

  let index = null;
  const parentSelector = getQuery(parent);
  // The element is a text node
  if (!element.localName) {
    // Find the index of the text node:
    index = Array.prototype.indexOf.call(parent.childNodes, element);
    return `${parentSelector}>textNode:nth-of-type(${index})`;
  }
  const jEl = $(element);
  index =
    jEl
      .parent()
      .find(`>${element.localName}`)
      .index(jEl) + 1;
  return `${parentSelector}>${element.localName}:nth-of-type(${index})`;
}

function store(selection, container, url, color, callback) {
  chrome.storage.local.get({highlights: {}}, result => {
    const {highlights} = result;

    if (!highlights[url]) highlights[url] = [];

    const count = highlights[url].push({
      version: STORE_FORMAT_VERSION,
      string: selection.toString(),
      container: getQuery(container),
      anchorNode: getQuery(selection.anchorNode),
      anchorOffset: selection.anchorOffset,
      focusNode: getQuery(selection.focusNode),
      focusOffset: selection.focusOffset,
      color,
    });
    chrome.storage.local.set({highlights});

    if (callback) callback(count - 1 + alternativeUrlIndexOffset);
  });
}

function update(highlightIndex, url, alternativeUrl, newColor) {
  chrome.storage.local.get({highlights: {}}, result => {
    const {highlights} = result;

    let urlToUse = url;
    let indexToUse = highlightIndex - alternativeUrlIndexOffset;
    if (highlightIndex < alternativeUrlIndexOffset) {
      urlToUse = alternativeUrl;
      indexToUse = highlightIndex;
    }

    const highlightsInKey = highlights[urlToUse];
    if (highlightsInKey) {
      const highlight = highlightsInKey[indexToUse];
      if (highlight) {
        highlight.color = newColor;
        chrome.storage.local.set({highlights});
      }
    }
  });
}

// alternativeUrl is optional
function loadAll(url, alternativeUrl) {
  chrome.storage.local.get({highlights: {}}, result => {
    let highlights = [];

    // Because of a bug in an older version of the code, some highlights were stored
    // using a key that didn't correspond to the full page URL. To fix this, if the
    // alternativeUrl exists, try to load highlights from there as well
    if (alternativeUrl) {
      highlights = highlights.concat(result.highlights[alternativeUrl] || []);
    }
    alternativeUrlIndexOffset = highlights.length;

    highlights = highlights.concat(result.highlights[url] || []);

    if (!highlights) return;

    for (let i = 0; i < highlights.length; i += 1) {
      load(highlights[i], i);
    }
  });
}

function load(highlightVal, highlightIndex, noErrorTracking) {
  const selection = {
    anchorNode: elementFromQuery(highlightVal.anchorNode),
    anchorOffset: highlightVal.anchorOffset,
    focusNode: elementFromQuery(highlightVal.focusNode),
    focusOffset: highlightVal.focusOffset,
  };

  const selectionString = highlightVal.string;
  const container = elementFromQuery(highlightVal.container);
  const {color} = highlightVal;

  if (!selection.anchorNode || !selection.focusNode || !container) {
    if (!noErrorTracking) {
    }
    return false;
  }

  const success = highlight(
    selectionString,
    container,
    selection,
    color,
    highlightIndex,
  );

  if (!noErrorTracking && !success) {
  }
  return success;
}

// alternativeUrl is optional
function clearPage(url, alternativeUrl) {
  chrome.storage.local.get({highlights: {}}, result => {
    const {highlights} = result;
    delete highlights[url];

    if (alternativeUrl) {
      // See 'loadAll()' for an explaination of why this is necessary
      delete highlights[alternativeUrl];
    }

    chrome.storage.local.set({highlights});
  });
}

// From an DOM element, get a query to that DOM element

// Colons and spaces are accepted in IDs in HTML but not in CSS syntax
// Similar (but much more simplified) to the CSS.escape() working draft
function escapeCSSString(cssString) {
  return cssString.replace(/(:)/gu, '\\$1');
}
