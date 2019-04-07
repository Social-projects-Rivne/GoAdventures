import React, { useState, useEffect } from 'react';
import { GalleryDto } from '../../../interfaces/Gallery.dto';
import './EditGallery.scss';
import { alterGallery } from '../../../api/gallery.service';

function EditGallery(props: GalleryDto) {
  console.debug(props);
  const [urls, setUrls] = useState([...props.imageUrls]);
  const uniKeyPrefix = 'uniID_gall_';

  useEffect(() => {
    async function req(): Promise<void> {
      const res = await alterGallery(props.id, {
        ...props,
        imageUrls: [...urls]
      });
      setUrls([...res]);
    }
    return () => {
      req();
      console.debug('clear subs');
    };
  }, [urls]);

  function handleDelete(index: number) {
    const tempArr = [...urls];
    tempArr.splice(index, 1);
    setUrls(tempArr);
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
