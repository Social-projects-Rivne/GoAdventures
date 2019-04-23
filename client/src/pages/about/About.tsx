import React, { Component } from "react";
import "./About.scss";

export const About = (props: any) => {
  return (
    <div className="container page-container about">
      <div className="Home-heading d-flex flex-column">
        <h2>GO Adventures</h2>
      </div>

      <header className="bg-primary text-center py-3 mb-4">
        <div className="container">
          <h1 className="font-weight-light text-white rounded">
            About project
          </h1>
        </div>
      </header>

      <p>
        Goadventure is a resource that has been created to unite people with
        common interests, namely, options for spending free time. We have great
        friends with whom it is nice to communicate, but that does not mean that
        you have to have a common way of relaxation. Some people like
        skateboarding, others are campaigning for a bike. I personally know
        examples of differences of opinion in this topic, because everyone wants
        to defend his hobby, sport, interests.
      </p>

      <p>
        So when you wanted to go hiking in the mountains, or fused on kayaks,
        but your friends say that they are not so interesting, what to do? Is a
        sociable person who likes different kinds of rest or extreme wants to be
        alone?
      </p>

      <p>
        For such people, we created a resource called "GoAdventure," already in
        its name it says "go travel." Thanks to him you will be able to create
        any event and as a result, your like-minded people can join your
        journey. You will get new acquaintances, impressions, and as with any
        extreme adrenaline. All that is needed is to create an event, specifying
        the name and description, specifying the location and the start time
        where you will be able to meet with the participants who will join you
        in the future.
      </p>

      <header className="bg-primary text-center py-3 mb-4">
        <div className="container">
          <h1 className="font-weight-light text-white rounded">
            Meet the Team
          </h1>
        </div>
      </header>

      <div className="card-deck">
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
        <div className="card border-0 shadow">
          <img
            src={require("../../assets/images/team/tina.jpeg")}
            className="card-img-top"
          />
          <div className="card-body text-center">
            <h5 className="card-title mb-0">Timofiy Lanevych</h5>
            <div className="card-text text-black-50">Likes back-end</div>
          </div>
        </div>
        <div className="card border-0 shadow">
          <img
            src={require("../../assets/images/team/ivant.jpg")}
            className="card-img-top"
          />
          <div className="card-body text-center">
            <h5 className="card-title mb-0">Vlad Osnovin</h5>
            <div className="card-text text-black-50">English in his blood</div>
          </div>
        </div>
        <div className="card border-0 shadow">
          <img
            src={require("../../assets/images/team/yarik.jpeg")}
            className="card-img-top"
          />
          <div className="card-body text-center">
            <h5 className="card-title mb-0">Yaroslav Dovbenko</h5>
            <div className="card-text text-black-50">The best SCRUM-master</div>
          </div>
        </div>{" "}
      </div>
    </div>
  );
};
