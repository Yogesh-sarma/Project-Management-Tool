
import { GET_ALL_USERNAMES } from "../actions/types";

const initialState = {
    usernames: []
};

export default function (state = initialState, action) {
    switch (action.type) {
        case GET_ALL_USERNAMES:
            return {
                ...state,
                usernames: action.payload
            }
        default:
            return state;
    }
}