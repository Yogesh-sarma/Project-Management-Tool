import { Component } from "react";
import { Alert } from "react-bootstrap";

class ErrorMessage extends Component{
    render(){
        const { message }= this.props;

        if(!message){
            return null
        }

        return(
            <Alert className="mt-3" variant="danger">
                {message}
            </Alert>
        )
    }
}


export default ErrorMessage;