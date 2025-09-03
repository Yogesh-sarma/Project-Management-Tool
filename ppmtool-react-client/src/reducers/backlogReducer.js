import { GET_BACKLOG, GET_PROJECT_TASK, DELETE_PROJECT_TASK, ADD_COMMENT_TO_PROJECT_TASK } from "../actions/types";

const initalState = {
    project_tasks: [],
    project_task: {},
    comments: []
}

export default function (state = initalState, action) {
    switch (action.type) {

        case GET_BACKLOG:
            return {
                ...state,
                project_tasks: action.payload
            }

        case GET_PROJECT_TASK:
            return {
                ...state,
                project_task: action.payload
            }

        case DELETE_PROJECT_TASK:
            return {
                ...state,
                project_tasks: state.project_tasks.filter(project_task => project_task.projectSequence !== action.payload)
            }

        case ADD_COMMENT_TO_PROJECT_TASK:
            return {
                ...state,
                project_task: action.payload
            }

        default:
            return state;
    }
}