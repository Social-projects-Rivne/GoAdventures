import React, { Component } from "react";
import "./About.scss";

export const About = (props: any) => {
  console.debug(props);
  return (
    <div className="container page-container">
      <header className="bg-primary text-center py-3 mb-4">
        <div className="container">
          <h1 className="font-weight-light text-white rounded">
            Meet the Team
          </h1>
        </div>
      </header>

      <div className="container">
        <div className="row">
          <div className="col-xl-3 col-md-6 mb-4">
            <div className="card border-0 shadow">
              <img
                src={require("../../assets/images/team/ivana.jpg")}
                className="card-img-top"
              />
              <div className="card-body text-center">
                <h5 className="card-title mb-0">Ivan Artiushok</h5>
                <div className="card-text text-black-50">Docker man</div>
              </div>
            </div>
            </div>

            <div className="col-xl-3 col-md-6 mb-4">
              <div className="card border-0 shadow">
                <img
                  src={require("../../assets/images/team/orest.jpg")}
                  className="card-img-top"
                />
                <div className="card-body text-center">
                  <h5 className="card-title mb-0">Orest Oleschuk</h5>
                  <div className="card-text text-black-50">Major frontend</div>
                </div>
              </div>
            </div>

            <div className="col-xl-3 col-md-6 mb-4">
              <div className="card border-0 shadow">
                <img
                  src={require("../../assets/images/team/ivant.jpg")}
                  className="card-img-top"
                />
                <div className="card-body text-center">
                  <h5 className="card-title mb-0">Ivan Tymoschuk</h5>
                  <div className="card-text text-black-50">Funny devil</div>
                </div>
              </div>
            </div>
         
        </div>
      </div>
    </div>
  );
};
