/**
 * Method to call the server with json body
 * @param {string} url - URL of the server
 * @param {object} data - Data to send to server
 * @param {string} method [method="POST"] - Http method to communicate.
 * @return {object} response object
 */
const callServer = async (url = "", data = {}, method = "POST") => {
  const response = await fetch(url, {
    method: method,
    body: JSON.stringify(data),
    headers: {
      "Content-Type": "application/json",
    },
  }).catch((error) => {
    console.error("Error:", error);
  });
  return response;
};

/**
 * Method to send get request.
 *
 * @param  {string} url - URL of the server
 * @param  {string} method [method="GET"] - HTTP method to communicate.
 * @return {object} response object
 */
const callServerWithoutBody = async (url = "", method = "GET") => {
  const response = await fetch(url, {
    method: method,
    headers: {
      "Content-Type": "application/json",
    },
  }).catch((error) => {
    console.error("Error:", error);
  });
  return response;
};

/**
 * Method to serialize the form data.
 *
 * @param {object} form Form node
 * @return {object} Serialized object
 */
const serializeForm = (form) => {
  var obj = {};
  var formData = new FormData(form);
  for (var key of formData.keys()) {
    obj[key] = formData.get(key);
  }
  return obj;
};
