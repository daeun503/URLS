function getHighlight() {
  const highlights = $('.highlighter--highlighted');

  const textToCopy = new Map(); // Use a Map instead of an object since it retains order of insertion

  Array.from(highlights).forEach(section => {
    const dataHighlightId = section.getAttribute('data-highlight-id');
    if (textToCopy.has(dataHighlightId)) {
      textToCopy.set(
        dataHighlightId,
        textToCopy.get(dataHighlightId).concat(section.textContent),
      );
    } else {
      textToCopy.set(dataHighlightId, [section.textContent]);
    }
  });

  // Join all strings corresponding to the same highlight together
  // Also, return an array instead of a Map since for some reason Maps don't get returned properly (serialization issue?)
  // Note that we could return a dict instead, but that we would lose ordering
  const highlightsText = [];
  textToCopy.forEach((value, key) => {
    const highlightText = value
      .map(text => text.replace(/\s+/gmu, ' '))
      .join('');
    highlightsText.push(parseInt(key, 10));
    highlightsText.push(highlightText);
  });
  return highlightsText;
}

getHighlight();
