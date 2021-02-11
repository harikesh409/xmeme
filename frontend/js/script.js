// Global object for host
// Change this value to update the backend URL
const host = "http://localhost:8081";

// Global notyf object to show alerts
const notyf = new Notyf({
  duration: 10000,
  dismissible: true,
  position: {
    x: "right",
    y: "top",
  },
});
// Create Meme Modal object
var memeModal = new mdb.Modal(document.getElementById("memeModal"), {});
// Global object for pageNumber.
var pageNumber = 0;

/**
 * Method to get memes from the server.
 *
 * @param {number} [pageNumber=0] - The pageNumber
 * @param {number} [pageSize=100] - The size of each Page
 * @return {JSON} Json response if success.
 */
const getMemes = async (pageNumber = 0, pageSize = 100) => {
  const response = await callServerWithoutBody(
    `${host}/memes?pageNumber=${pageNumber}&pageSize=${pageSize}`
  );
  if (response.status == 200) return response.json();
  notyf.error("Some error occured while getting memes");
};

/**
 * Method to add the meme data to DOM
 *
 * @param {Array} memes - Memes array.
 * @param {boolean} [prependFlag=false] - Flag to indicate append or prepend.
 */
const addMemeData = (memes, prependFlag = false) => {
  let memeContent = document.querySelector(".memes-content .row");
  console.log(memes);
  memes.forEach((memeData) => {
    const content = `<div class="col-xxl-3 col-lg-4 col-md-6 col-sm-12 mb-3">
            <div class="card" data-meme-id="${memeData.id}">
              <div class="bg-image hover-overlay ripple" data-mdb-ripple-color="dark">
                <img src="${memeData.url}" data-original-src="${memeData.url}" alt="${memeData.caption}" style="width:320px;height:215px" class="img-fluid" onerror="this.onerror=null; this.src='img/not-found.jpg'"  />
                <a href="#!">
                  <div class="mask" style="background-color: rgba(251, 251, 251, 0.15)"></div>
                </a>
              </div>
              <div class="card-body">
                <h5 class="card-title">${memeData.caption}</h5>
              </div>
                <div class="card-footer">
                <span class="text-muted">${memeData.name}</span>
                <span class="float-end delete-meme" onclick="deleteMeme(${memeData.id})"><i class="fas fa-trash"></i></span>
                <span class="float-end edit-meme me-2" onclick="editMeme(${memeData.id})"><i class="fas fa-edit"></i></span>
                </div>
            </div>
          </div>`;
    if (prependFlag) {
      memeContent.insertAdjacentHTML("afterbegin", content);
    } else {
      memeContent.innerHTML += content;
    }
  });
};

// On Load method
(async function () {
  document.querySelector("main").style.display = "none";
  const response = await getMemes(pageNumber, 100);
  addMemeData(response);
  document.querySelector("#loader").style.display = "none";
  document.querySelector("main").style.display = "block";
})();

// On Scroll event listner
const lazyLoadContent = async () => {
  var doc = document.documentElement;
  var topHeight = (window.pageYOffset || doc.scrollTop) - (doc.clientTop || 0);
  if (doc.scrollHeight <= window.innerHeight + topHeight) {
    document.querySelector("#loader").style.display = "block";
    const response = await getMemes(++pageNumber, 100);
    if (response.length > 0) addMemeData(response);
    else window.removeEventListener("scroll", lazyLoadContent);
    document.querySelector("#loader").style.display = "none";
  }
};
window.addEventListener("scroll", lazyLoadContent);

var editMemeModal = new mdb.Modal(
  document.querySelector("#updateMemeModal"),
  {}
);

/**
 * Method to show the selected meme data in the model.
 *
 * @param {number} memeId - The meme identifier to edit.
 */
const editMeme = (memeId) => {
  const url = document
    .querySelector(`[data-meme-id='${memeId}'] img`)
    .getAttribute("data-original-src");
  const caption = document.querySelector(
    `[data-meme-id='${memeId}'] .card-title`
  ).innerHTML;
  const name = document.querySelector(
    `[data-meme-id='${memeId}'] .card-footer .text-muted`
  ).innerHTML;
  document.querySelector("#edit-name").innerHTML = name;
  document.querySelector("#edit-id").value = memeId;
  document.querySelector("#edit-memeUrl").value = url;
  document.querySelector("#edit-caption").value = caption;
  editMemeModal.show();
};

const updateForm = document.querySelector("#updateMeme");
// Event listner to update the meme.
updateForm.addEventListener("submit", async (e) => {
  e.preventDefault();
  if (updateForm.checkValidity()) {
    editMemeModal.hide();
    document.querySelector("#loader").style.display = "block";
    const formData = serializeForm(updateForm);
    const memeId = formData.id;
    const response = await callServer(
      `${host}/memes/${memeId}`,
      formData,
      "PATCH"
    );
    console.log(response.status);
    if (response.status == 204) {
      document.querySelector(
        `[data-meme-id='${memeId}'] .card-title`
      ).innerHTML = formData.caption;
      document.querySelector(`[data-meme-id='${memeId}'] img`).src =
        formData.url;
      document.querySelector("#loader").style.display = "none";
      notyf.success("Successfully updated the meme");
    } else if (response.status == 304) {
      document.querySelector("#loader").style.display = "none";
      notyf.success("Successfully updated the meme");
    } else {
      document.querySelector("#loader").style.display = "none";
      notyf.error(
        "Sorry some error occured while updating the meme. Please try again after sometime."
      );
    }
  }
});

/**
 * Method to delete a meme.
 *
 * @param {Integer} memeId Unique identifier of the meme.
 */
const deleteMeme = async (memeId) => {
  const canDelete = confirm("Are you sure to delete the meme?");
  if (canDelete) {
    document.querySelector("#loader").style.display = "block";
    const response = await callServerWithoutBody(
      `${host}/memes/${memeId}`,
      "DELETE"
    );
    console.log(response.status);
    if (response.status == 204) {
      document
        .querySelector(`[data-meme-id='${memeId}']`)
        .parentElement.remove();
      document.querySelector("#loader").style.display = "none";
      notyf.success("Meme delete successfully.");
    } else if (response.status == 404) {
      document.querySelector("#loader").style.display = "none";
      notyf.success("Meme delete successfully.");
    } else {
      document.querySelector("#loader").style.display = "none";
      notyf.error(
        "Sorry some error occured while deleting the meme. Please try again after sometime."
      );
    }
  }
};

const createForm = document.querySelector("#addMeme");
// Event listner to create the meme.
createForm.addEventListener("submit", async (e) => {
  e.preventDefault();
  if (createForm.checkValidity()) {
    memeModal.hide();
    document.querySelector("#loader").style.display = "block";
    const formData = serializeForm(createForm);
    console.log(formData);
    const response = await callServer(`${host}/memes`, formData, "POST");
    console.log(response);
    if (response.status == 201) {
      const parsedResponse = await response.json();
      console.log(parsedResponse);
      formData["id"] = parsedResponse.id;
      addMemeData([formData], true);
      notyf.success("Successfully added the meme.");
      resetModal();
    } else if (response.status == 409) {
      notyf.error("Meme already exists.");
    } else if (response.status == 400) {
      const parsedResponse = await response.json();
      parsedResponse.errors.forEach((error) => {
        notyf.error(error.split(":")[1]);
      });
    } else {
      notyf.error(
        "Sorry some error occured while adding the meme. Please try again after sometime."
      );
    }
    document.querySelector("#loader").style.display = "none";
  }
});

/**
 * Method to reset the add meme form.
 *
 */
const resetModal = () => {
  document.querySelector("#addMeme").reset();
  document.querySelector("#addMeme").classList.remove("was-validated");
};
