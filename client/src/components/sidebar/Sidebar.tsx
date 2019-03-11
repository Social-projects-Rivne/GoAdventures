import React, { Component } from 'react';
import { UserDto } from '../../interfaces/User.dto';
import './Sidebar.scss';


export const Sidebar = (props: UserDto) => {

    function handleClick() {                                //як передати змінні інпута в стейт



    }

    function handleSubmit() {
        console.log('form is submitted')

    }


    return (
        <div className='Sidebar-wrapper'>
            <div className='Sidebar__avatar'>
                <img src="https://i.ytimg.com/vi/aVeCYjAiQHo/maxresdefault.jpg" alt="user_avatar" />
            </div>
            <div className="Sidebar__userInfo">
                <ul className="list-group list-group-flush">
                    <li className="list-group-item">email: {props.email}</li>
                    <li className="list-group-item">username: {props.userName} </li>
                    <li className="list-group-item">fullname: {props.fullName}</li>
                    <li className="list-group-item"> <button type="button" onClick={handleClick} className="btn btn-primary" > Edit Profile </button></li>

                    <li className="list-group-item">

                        <label htmlFor="exampleInputEmail1">Email address</label>
                        <input type="email" className="form-control" id="exampleInputEmail1"
                               aria-describedby="emailHelp" placeholder="Enter email" onSubmit={handleClick}/> </li>


                    <li className="list-group-item">

                        <label htmlFor="exampleInput">Username</label>
                        <input type="text" className="form-control" id="exampleInput"
                               aria-describedby="inputHelp" placeholder="Enter username"/> </li>

                    <li className="list-group-item">

                        <label htmlFor="exampleInput">Fullname</label>
                        <input type="text" className="form-control" id="exampleInput"
                               aria-describedby="inputHelp" placeholder="Enter fullname"/> </li>

                    <li className="list-group-item"> <button type="button" onClick={handleSubmit} className="btn btn-primary"> Save </button></li>








                </ul>
            </div>
        </div>


    );






}