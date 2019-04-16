import React, { useState, useEffect } from 'react';
import { GalleryDto } from '../../../interfaces/Gallery.dto';
import './EditGallery.scss';

interface EditGalleryPorps {
  editGallery: GalleryDto;
  setGallery: any;
  setErrors: any;
}

function EditGallery(props: EditGalleryPorps) {
  const [urls, setUrls] = useState([] as string[]);
  const uniKeyPrefix = 'uniID_gall_';
  /*  Set initial state */
  useEffect(() => {
    if (
      Object.entries(props.editGallery).length > 0 &&
      !Object.is(props.editGallery.imageUrls, urls)
    ) {
      setUrls([...props.editGallery.imageUrls]);
    }
    return () => {};
  }, [props.editGallery.imageUrls]);

  useEffect(() => {
    if (Object.entries(props.editGallery).length > 0 && urls.length > 0) {
      props.setGallery({ ...props.editGallery, imageUrls: urls });
    }
    return () => {};
  }, [urls]);

  async function handleDelete(index: number): Promise<void> {
    const mutatedUrls = [...urls];
    await mutatedUrls.splice(index, 1);
    setUrls(mutatedUrls);
  }

  return (
    <div className='EditGallery jumbotron'>
      <div className='d-flex flex-row flex-grow-1 flex-shrink-1 flex-wrap justify-content-center'>
        {urls.map((image: string, index: number) => {
          return (
            <div key={uniKeyPrefix + index}>
              <img src={image} alt='image' className='img-thumbnail' />
              <button
                onClick={(
                  e: React.MouseEvent<HTMLButtonElement, MouseEvent>
                ) => {
                  handleDelete(index);
                }}
                type='button'
                className='ml-2 mb-1 close'
                aria-label='Close'
              >
                <span aria-hidden='true'>&times;</span>
              </button>
            </div>
          );
        })}
      </div>
    </div>
  );
}

export default EditGallery;
