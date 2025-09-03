import { Component } from "react";

class Comment extends Component{

    constructor(props){
        super(props);
        console.log(props);
    }

    render(){
        return(
            <div>
                {this.props.comments.map((comment) => (
                    <div className="border-bottom mb-3 py-1" key={comment.id}>
                        <b>{comment.username}</b>:   {comment.comment}
                    </div>
                ))}
            </div>
        )
    }
}

export default Comment;