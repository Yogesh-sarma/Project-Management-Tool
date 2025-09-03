import axios from 'axios';
import { GET_ERRORS, GET_BACKLOG, GET_PROJECT_TASK, DELETE_PROJECT_TASK, ADD_COMMENT_TO_PROJECT_TASK } from './types';



export const addProjectTask = (backlog_id, project_task, history) => async dispatch => {

    try {
        await axios.post(`http://localhost:8080/api/backlog/${backlog_id}`, project_task);
        history.push(`/projectBoard/${backlog_id}`);
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

export const getBacklog = backlog_id => async dispatch => {
    try {
        const res = await axios.get(`http://localhost:8080/api/backlog/${backlog_id}`);
        dispatch({
            type: GET_BACKLOG,
            payload: res.data
        })
    } catch (err) {
        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        })
    }
};

export const getProjectTask = (backlog_id, pt_id, history) => async dispatch => {
    try {
        const res = await axios.get(`http://localhost:8080/api/backlog/${backlog_id}/${pt_id}`);
        dispatch({
            type: GET_PROJECT_TASK,
            payload: res.data
        })
    } catch (err) {
        history.push("/dashboard");
    }

};

export const updateProjectTask = (backlog_id, pt_id, project_task, history) => async dispatch => {
    try {
        await axios.patch(`http://localhost:8080/api/backlog/${backlog_id}/${pt_id}`, project_task);
        history.push(`/projectBoard/${backlog_id}`);
        dispatch({
            type: GET_ERRORS,
            payload: {}
        });
    } catch (err) {

        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        });

    }
};

export const deleteProjectTask = (backlog_id, pt_id) => async dispatch => {
    try{
        if (window.confirm(`You are deleting Project Task ${pt_id}, this action can not be undone!`)) {
            await axios.delete(`http://localhost:8080/api/backlog/${backlog_id}/${pt_id}`);
            dispatch({
                type: DELETE_PROJECT_TASK,
                payload: pt_id
            });
        }
    }
    catch (err) {
        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        });
    }
}


export const addCommentToProjectTask = (comment,backlog_id, pt_id) => async dispatch => {
    const res = await axios.post(`http://localhost:8080/api/backlog/comment/${backlog_id}/${pt_id}`, comment);
    dispatch({
        type: ADD_COMMENT_TO_PROJECT_TASK,
        payload: res.data
    })
}