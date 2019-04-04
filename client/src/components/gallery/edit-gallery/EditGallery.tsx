import React from "react";
import { GalleryDto } from "../../../interfaces/Gallery.dto";

function EditGallery(props: GalleryDto["imageUrls"]) {
  return (
    <div className="d-flex flex-row flex-grow-1 flex-shrink-1 flex-wrap">
      {/* props */}
      <div>
        <img
          src="https://via.placeholder.com/200"
          alt="image"
          className="img-thumbnail"
        />
        <button
          type="button"
          className="ml-2 mb-1 close"
          data-dismiss="toast"
          aria-label="Close"
        >
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
    </div>
  );
}

export default EditGallery;
