export function convertToCamelCase(inputString) {
    return inputString.replace(/(?:^|_)([a-z])/g, (_, letter) => letter.toUpperCase());
}

export function formatDuration(duration) {
    const hoursMatch = duration.match(/(\d+)H/);
    const minutesMatch = duration.match(/(\d+)M/);
    const secondsMatch = duration.match(/(\d+)S/);

    let formattedDuration = '';
  
    if (hoursMatch) {
      formattedDuration += `${hoursMatch[1]}H `;
    }
  
    if (minutesMatch) {
      formattedDuration += `${minutesMatch[1]}M `;
    }
  
    if (secondsMatch) {
      formattedDuration += `${secondsMatch[1]}S`;
    }
  
    return formattedDuration.trim();
  }