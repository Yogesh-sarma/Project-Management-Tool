import axios from 'axios';
import {GET_ALL_USERNAMES } from './types';

export const getAllUsers = () => async dispatch => {
    try{
        const res = await axios.get('http://localhost:8080/api/users/all')
        dispatch({
            type: GET_ALL_USERNAMES,
            payload: res.data
        })
    } catch (error){
        console.log("Error in fetching users" + error);
    }
}