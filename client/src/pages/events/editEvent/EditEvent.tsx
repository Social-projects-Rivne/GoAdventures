import React, { createRef, useState, useEffect, ChangeEvent } from "react";
import { EventDto } from "../../../interfaces/Event.dto";
import DatePicker from "react-datepicker";
import LCG from "leaflet-control-geocoder";
import { TileLayer, Popup, Marker, Map } from "react-leaflet";
import {
  ValidatedTextarea,
  DropDown,
  ErrorMessageComponent,
  UploadInput
} from "../../../components";
import { eventSchema } from "../../../validationSchemas/eventValidation";
import { editEventFormInputSettings } from "./inputSettings";
import { GalleryDto } from "../../../interfaces/Gallery.dto";
import {
  Formik,
  Form,
  Field,
  FormikProps,
  FieldProps,
  FormikValues
} from "formik";
import { MdDone } from "react-icons/md";
import moment from "moment";
import { updateEvent } from "../../../api/event.service";
import "./EditEvent.scss";
import { ErrorMessage } from "../../../interfaces/ErrorMessage";

interface EditEvent {
  event: EventDto;
  setEdit: any;
  setIsLoading: any;
  setEvent: any;
}

interface EditEventInputState {
  topic: string;
  location: string;
}

export const EditEvent = (props: EditEvent) => {
  const zoom = 13;
  const geocoder = LCG.L.Control.Geocoder.nominatim();
  let submitNested: () => any;
  const bindSubmit = (formikSubmit: any) => {
    submitNested = formikSubmit;
  };
  const [errors, setErrors] = useState({} as ErrorMessage);
  const [datepick, setDate] = useState({
    startDate: props.event.startDate,
    endDate: props.event.endDate
  });
  const [category, setCategory] = useState(props.event.category);
  const [eventDialog, setEventDialog] = useState({
    topic: props.event.topic
  } as EditEventInputState);
  const [gallery, setGallery] = useState(props.event.gallery as GalleryDto);
  const [description, setDescription] = useState(props.event.description);
  const [location, setLocation] = useState(props.event.location);
  const [mapCoordiantes, setMapCoordinates] = useState([
    props.event.latitude,
    props.event.longitude
  ]);
  const [fetch, setFetch] = useState(false);

  useEffect(() => {
    if (fetch) {
      const update = async () => {
        props.setIsLoading(true);
        const response = await updateEvent({
          category,
          description,
          latitude: mapCoordiantes[0],
          ...props.event,
          ...eventDialog,
          ...datepick,
          gallery,
          location,
          longitude: mapCoordiantes[1]
        });
        props.setEvent({ ...response });
      };
      setFetch(false);
      update();
      props.setEdit(false);
      props.setIsLoading(false);
      return () => {};
    } else {
      return () => {};
    }
  }, [fetch]);

  const markerRef = createRef<Marker>();
  const mapRef = createRef<Map>();
  const formRef = createRef<Formik>();
  return (
    <div className="page-container row justify-content-center">
      <h2>Edit event info</h2>
      {errors.errorMessage ? <ErrorMessageComponent {...errors} /> : null}
      <div className="col-12">
        <h2>Change event description</h2>
        <div className="row form-group">
          <Formik
            ref={formRef}
            validationSchema={eventSchema}
            initialValues={{ topic: eventDialog.topic, gallery: "" }}
            enableReinitialize={true}
            validateOnBlur={true}
            validateOnChange={true}
            onSubmit={(values: any, actions) => {
              setEventDialog({ ...values });
              actions.setSubmitting(false);
            }}
            render={(formikProps: FormikProps<FormikValues>) => (
              <Form className="d-flex flex-column w-100">
                {editEventFormInputSettings.map((input, index) => {
                  return (
                    <label key={index}>
                      {input.label_value}
                      <Field
                        name={input.field_name}
                        render={({ field, form }: FieldProps<FormikValues>) => {
                          return (
                            <div className="mb-3">
                              <input
                                type={input.type}
                                multiple
                                className="form-control rounded"
                                placeholder="firstName"
                                {...field}
                              />
                              {form.touched[input.field_name] &&
                              form.errors[input.field_name] &&
                              form.errors[input.field_name] ? (
                                <div className="invalid-feedback">
                                  {form.errors[input.field_name]}
                                </div>
                              ) : (
                                <div className="valid-feedback">
                                  <MdDone /> Update info for your event
                                </div>
                              )}
                            </div>
                          );
                        }}
                      />
                    </label>
                  );
                })}
              </Form>
            )}
          />
        </div>
        <div className="row form-group">
          <ValidatedTextarea
            {...{
              bindSubmit,
              labelText: "Description",
              initialValue: { textarea: description },
              handleSubmit: setDescription
            }}
          />
        </div>
        <div className="form-group row">
          <label
            className="col-sm-4 col-form-label text-right"
            htmlFor="Category"
          >
            Choose category for the event
          </label>
          <div className="col-sm-8">
            <DropDown onCategoryChange={setCategory} />
          </div>
        </div>
        <div className="form-group row">
          <label
            className="col-sm-4 col-form-label text-right"
            htmlFor="StartDate"
          >
            Start date
          </label>
          <div className="col-sm-3">
            <DatePicker
              id="StartDate"
              className="input-group-text"
              selected={moment(datepick.startDate).toDate()}
              onChange={(e: Date): void => {
                setDate({ ...datepick, startDate: moment(e).toISOString() });
              }}
              showTimeSelect
              timeFormat="HH:mm"
              timeIntervals={15}
              timeCaption="time"
              withPortal
              dateFormat="MMMM d, yyyy h:mm aa"
            />
          </div>

          <label
            className="col-sm-2 col-form-label text-right"
            htmlFor="EndDate"
          >
            End date
          </label>
          {props.event.endDate === "0" && datepick.endDate != undefined ? (
            <div className="col-sm-3">
              <DatePicker
                id="EndDate"
                className="input-group-text"
                selected={
                  datepick.endDate != "0"
                    ? moment(datepick.endDate).toDate()
                    : moment().toDate()
                }
                onChange={(e: Date): void => {
                  setDate({ ...datepick, endDate: moment(e).toISOString() });
                }}
                showTimeSelect
                timeFormat="HH:mm"
                timeIntervals={15}
                timeCaption="time"
                withPortal
                dateFormat="MMMM d, yyyy h:mm aa"
              />
            </div>
          ) : (
            <div className="col-sm-3">
              <DatePicker
                id="EndDate"
                className="input-group-text"
                selected={moment(datepick.endDate).toDate()}
                onChange={(e: Date): void => {
                  setDate({ ...datepick, endDate: moment(e).toISOString() });
                }}
                showTimeSelect
                timeFormat="HH:mm"
                timeIntervals={15}
                timeCaption="time"
                withPortal
                dateFormat="MMMM d, yyyy h:mm aa"
              />
            </div>
          )}
        </div>
      </div>
      <div className="col-12">
        <h3>Update your event gallery</h3>
        <UploadInput
          {...{
            gallery,
            setErrors,
            setGallery,
            eventId: props.event.id
          }}
        />
      </div>
      <div className="col-12">
        <h3>Update event location</h3>
        <h5>Event location: {location}</h5>
        <div>
          <Map
            attributionControl={true}
            zoomControl={false}
            doubleClickZoom={true}
            scrollWheelZoom={true}
            dragging={true}
            animate={true}
            easeLinearity={0.35}
            ref={mapRef}
            onclick={e => {
              const currentMarker = markerRef.current;
              if (!!currentMarker) {
                const latLng = currentMarker.leafletElement.getLatLng();
                geocoder.reverse(latLng, zoom, (result: any) => {
                  setLocation(result[0].name);
                });
                setMapCoordinates([latLng.lat, latLng.lng]);
                setMapCoordinates([e.latlng.lat, e.latlng.lng]);
              }
            }}
            center={[mapCoordiantes[0], mapCoordiantes[1]]}
            zoom={zoom}
            className="rounded"
          >
            <TileLayer
              attribution='&amp;copy <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
              url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            />

            <Marker
              draggable={true}
              ondrag={() => {
                const currentMarker = markerRef.current;
                if (!!currentMarker) {
                  const latLng = currentMarker.leafletElement.getLatLng();
                  geocoder.reverse(latLng, zoom, (result: any) => {
                    setLocation(result[0].name);
                  });
                  setMapCoordinates([latLng.lat, latLng.lng]);
                }
              }}
              position={[mapCoordiantes[0], mapCoordiantes[1]]}
              ref={markerRef}
            >
              <Popup>
                A pretty CSS3 popup. <br /> Easily customizable.
              </Popup>
            </Marker>
          </Map>
        </div>
      </div>
      <div className="row justify-content-center btns-content">
        <button
          type="button"
          name="cancel"
          className="btn btn-danger m-1"
          onClick={() => {
            props.setEdit(false);
          }}
        >
          Cancel
        </button>
        <button
          type="button"
          name="update"
          className="btn btn-primary m-1"
          onClick={async () => {
            const formCurrent = formRef.current;
            if (formCurrent) {
              await submitNested();
              await formCurrent.submitForm();
              setFetch(true);
            }
          }}
        >
          Update
        </button>
      </div>
    </div>
  );
};
