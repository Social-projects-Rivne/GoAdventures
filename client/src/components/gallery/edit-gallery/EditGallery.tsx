import React, { useState, useEffect } from 'react';
import { GalleryDto } from '../../../interfaces/Gallery.dto';
import './EditGallery.scss';
import { alterGallery, getGallery } from '../../../api/gallery.service';

interface EditGalleryPorps {
  editGallery: GalleryDto;
  setDialog: any;
}

function EditGallery(props: EditGalleryPorps) {
  const [gallery, setGallery] = useState({} as GalleryDto);
  const [urls, setUrls] = useState([] as string[]);
  const uniKeyPrefix = 'uniID_gall_';

  /*  Set initial state */
  useEffect(() => {
    setUrls([...props.editGallery.imageUrls]);
    setGallery({ ...props.editGallery });
    console.debug('initial effect', gallery);
    return () => {};
  }, [props.editGallery]);

  useEffect(() => {
    async function req(urlsHasElements: boolean): Promise<void> {
      let res: any;
      urlsHasElements
        ? ((res = await alterGallery(props.editGallery.id, {
            ...props.editGallery,
            imageUrls: [...urls]
          })) /*  Cause error
          props.setDialog({ gallery: { ...res } }), */,
          setUrls([...res.imageUrls]))
        : (res = { ...props.editGallery });
    }
    return () => {
      req(urls.length > 0);
    };
  }, [urls]);

  function handleDelete(index: number) {
    const mutatedUrls = [...urls];
    mutatedUrls.splice(index, 1);
    setUrls([...mutatedUrls]);
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
