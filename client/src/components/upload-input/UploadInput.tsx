import React, { ChangeEvent, useState } from 'react';
import EditGallery from '../gallery/edit-gallery/EditGallery';
import { uploadGallery } from '../../api/gallery.service';
import { GalleryDto } from '../../interfaces/Gallery.dto';

interface UploadInputProps {
  setErrors: any;
  setGallery: any;
  gallery?: GalleryDto;
  eventId?: number;
}

export const UploadInput = (props: UploadInputProps) => {
  const [gallery, setGallery] = useState({ ...props.gallery } as GalleryDto);
  const validateFile = (fileList: FileList): boolean => {
    let file;
    // tslint:disable-next-line: prefer-for-of
    for (let i = 0; i < fileList.length; i++) {
      file = fileList[i].type.match(/^.*\b(png|jpg|gif)\b.*$/);
    }
    if (file) {
      return true;
    } else {
      props.setErrors({
        errorMessage: 'You try to upload file with unsoported extension'
      });
      return false;
    }
  };
  return (
    <div>
      <div className='input-group mb-3 rounded'>
        <div className='input-group-prepend'>
          <span className='input-group-text rounded' id='inputFileUpload'>
            Upload
          </span>
        </div>
        <div className='custom-file'>
          <input
            onChange={async (
              e: ChangeEvent<HTMLInputElement>
            ): Promise<void> => {
              const fileList = e.target.files;
              if (fileList) {
                const formData = new FormData();
                for (
                  let i = 0;
                  i < fileList.length && validateFile(fileList);
                  i++
                ) {
                  formData.append('images', fileList[i]);
                }
                const response = await uploadGallery(
                  formData,
                  props.eventId ? props.eventId : undefined
                );
                props.setGallery({ ...response });
                setGallery({ ...response });
              }
            }}
            type='file'
            multiple
            className='custom-file-input rounded'
            id='inputFileUpload'
            aria-describedby='fileUpload'
          />
          <label
            className='custom-file-label rounded'
            htmlFor='inputFileUpload'
          >
            Choose file
          </label>
        </div>
      </div>
      <EditGallery
        {...{
          editGallery: { ...gallery },
          setErrors: props.setErrors,
          setGallery: props.setGallery
        }}
      />
    </div>
  );
};
