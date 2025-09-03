import axios from 'axios';
import { GET_ERRORS, GET_PROJECTS, GET_PROJECT, DELETE_PROJECT, GET_PROJECT_USERNAMES } from './types';

export const createProject = (project, history) => async dispatch => {
    try {
        await axios.post("http://localhost:8080/api/project/create", project)
        history.push("/dashboard");
        dispatch({
            type: GET_ERRORS,
            payload: {}
        });
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }
};

export const getProjects = () => async dispatch => {
    const res = await axios.get("http://localhost:8080/api/project/all")
    dispatch({
        type: GET_PROJECTS,
        payload: res.data
    })
}

export const getProject = (id, history) => async dispatch => {
    try {
        const res = await axios.get(`http://localhost:8080/api/project/${id}`)
        dispatch({
            type: GET_PROJECT,
            payload: res.data
        })
    } catch (error) {
        history.push('/dashboard');
    }
}

export const deleteProject = id => async dispatch => {
    try{
        if (window.confirm("ARE YOU SURE? This will delete the Project and all the data related to it!")) {
            await axios.delete(`http://localhost:8080/api/project/${id}`);
            dispatch({
                type: DELETE_PROJECT,
                payload: id
            });
        }
    }
    catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }
}

export const getProjectUsers = (id) => async dispatch => {
    try{
        const res = await axios.get(`http://localhost:8080/api/project/${id}/usernames`)
        dispatch({
            type: GET_PROJECT_USERNAMES,
            payload: res.data
        })
    } catch (error){
        console.log("Error in fetching users" + error);
    }
}