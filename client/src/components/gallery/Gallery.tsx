import React from 'react';
import { GalleryDto } from '../../interfaces/Gallery.dto';

// WILL CHANGE!

export const Gallery = (props?: GalleryDto): JSX.Element => {
  console.debug(props);
  return (
    <div
      id='carouselExampleControls'
      className='carousel slide'
      data-ride='carousel'
    >
      <div className='carousel-inner'>
        {!!props!.imageUrls && props!.imageUrls.length > 0 ? (
          props!.imageUrls.map((imageUrl, index) => (
            <div
              key={index}
              className={`carousel-item ${index === 0 ? 'active' : ''}`}
            >
              <img src={imageUrl} className='d-block w-100' alt='...' />
            </div>
          ))
        ) : (
          <div className='carousel-item active'>
            <img
              src='https://via.placeholder.com/500'
              className='d-block w-100'
              alt='...'
            />
          </div>
        )}
      </div>
      <a
        className='carousel-control-prev'
        href='#carouselExampleControls'
        role='button'
        data-slide='prev'
      >
        <span className='carousel-control-prev-icon' aria-hidden='true' />
        <span className='sr-only'>Previous</span>
      </a>
      <a
        className='carousel-control-next'
        href='#carouselExampleControls'
        role='button'
        data-slide='next'
      >
        <span className='carousel-control-next-icon' aria-hidden='true' />
        <span className='sr-only'>Next</span>
      </a>
      <a id='' className='btn btn-success carousel-indicators' role='button'>
        Add images
      </a>
    </div>
  );
};
