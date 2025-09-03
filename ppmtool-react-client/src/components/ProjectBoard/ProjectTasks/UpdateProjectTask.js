import React, { Component } from 'react'
import { connect } from 'react-redux';
import classnames from 'classnames';
import { getProjectTask, updateProjectTask, addCommentToProjectTask } from '../../../actions/backlogActions';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import Comment from '../Comment';
import { getProjectUsers } from '../../../actions/projectActions';

class UpdateProjectTask extends Component {

    constructor() {
        super()

        this.state = {
            id: "",
            projectSequence: "",
            summary: "",
            acceptanceCriteria: "",
            status: "",
            priority: "",
            dueDate: "",
            projectIdentifier: "",
            created_At: "",
            comments: [],
            username: "",
            comment: "",
            projectUsers: [],
            errors: {}
        };
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.onCommentClick = this.onCommentClick.bind(this);
    }

    componentDidMount() {
        const { backlog_id, pt_id } = this.props.match.params;
        this.props.getProjectTask(backlog_id, pt_id, this.props.history)
        this.props.getProjectUsers(backlog_id)
       
    }

    componentWillReceiveProps(nextProps) {

        if (nextProps.errors) {
            this.setState({ errors: nextProps.errors });
        }

        if(nextProps.project.usernames){
            this.setState({projectUsers: nextProps.project.usernames});
        }

        const {
            id,
            projectSequence,
            summary,
            acceptanceCriteria,
            status,
            priority,
            projectIdentifier,
            dueDate,
            created_At,
            comments,
            username
        } = nextProps.project_task;

        this.setState({
            id,
            projectSequence,
            summary,
            acceptanceCriteria,
            status,
            priority,
            projectIdentifier,
            dueDate,
            created_At,
            comments,
            username
        });

        // if(nextProps.comments.lengthcomments){
        //     this.setState({
        //         comments: nextProps.comments
        //     })
        // }
    }

    onChange(e) {
        this.setState({ [e.target.name]: e.target.value })
    }

    onSubmit(e) {
        e.preventDefault();
        const UpdateProjectTask = {
            id: this.state.id,
            projectSequence: this.state.projectSequence,
            summary: this.state.summary,
            acceptanceCriteria: this.state.acceptanceCriteria,
            status: this.state.status,
            priority: this.state.priority,
            projectIdentifier: this.state.projectIdentifier,
            dueDate: this.state.dueDate,
            created_At: this.state.created_At,
            updated_At: this.state.updated_At,
            comments: this.state.comments,
            username: this.state.username
        }
        console.log(UpdateProjectTask);
        this.props.updateProjectTask(this.state.projectIdentifier, this.state.projectSequence, UpdateProjectTask, this.props.history);
    }

    onCommentClick(e){
        const commentText = this.state.comment;
        if(commentText.trim() !==""){
            const CommentBody ={
                comment: commentText.trim(),
                username:""
            }

            this.props.addCommentToProjectTask(CommentBody,this.state.projectIdentifier, this.state.projectSequence);
            this.setState({
                comment: ""
            })
        }
    }


    render() {

        const { errors } = this.state;

        return (
            <div className="add-PBI">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 pb-4 m-auto">
                            <Link to={`/projectBoard/${this.state.projectIdentifier}`} className="btn btn-light">
                                Back to Project Board
                            </Link>
                            <h4 className="display-4 text-center"> Update Project Task</h4>
                            <p className="lead text-center"> Project Name: {this.state.projectIdentifier} |  Project Task ID:{""} {this.state.projectSequence} {""}</p>
                            <form onSubmit={this.onSubmit}>
                                <div className="form-group">
                                    <input type="text" className={classnames("form-control form-control-lg", {
                                        "is-invalid": errors.summary
                                    })} name="summary" placeholder="Project Task summary" value={this.state.summary} onChange={this.onChange} />
                                    {
                                        errors.summary && (
                                            <div className="invalid-feeback">{errors.summary}</div>
                                        )
                                    }
                                </div>
                                <div className="form-group">
                                    <textarea className="form-control form-control-lg" placeholder="Acceptance Criteria" name="acceptanceCriteria" value={this.state.acceptanceCriteria} onChange={this.onChange}></textarea>
                                </div>
                                <h6>Due Date</h6>
                                <div className="form-group">
                                    <input type="date" className="form-control form-control-lg" name="dueDate" value={this.state.dueDate} onChange={this.onChange} />
                                </div>
                                <div className="form-group">
                                    <select className="form-control form-control-lg" name="priority" value={this.state.priority} onChange={this.onChange}>
                                        <option value={0}>Select Priority</option>
                                        <option value={1}>High</option>
                                        <option value={2}>Medium</option>
                                        <option value={3}>Low</option>
                                    </select>
                                </div>

                                <div className="form-group">
                                    <select className="form-control form-control-lg" name="status" value={this.state.status} onChange={this.onChange}>
                                        <option value="">Select Status</option>
                                        <option value="TO_DO">TO DO</option>
                                        <option value="IN_PROGRESS">IN PROGRESS</option>
                                        <option value="DONE">DONE</option>
                                    </select>
                                </div>

                                <div className="form-group">
                                    <select className="form-control form-control-lg" name="username" value={this.state.username} onChange={this.onChange}>
                                        {this.state.projectUsers.map((user) => (
                                            <option key={user} value={user}>
                                                {user}
                                            </option>
                                        ))}
                                    </select>
                                </div>

                                <input type="submit" className="btn btn-primary btn-block mt-4" />
                            </form>
                        </div>
                        <div className="col-md-8 m-auto pt-4 bg-light">
                            <div className='border-bottom mb-3'>Comments: </div>
                            {this.state.comments && this.state.comments.length>0 && <Comment comments={this.state.comments}/>}
                            <div className="form-group">
                                <textarea className="form-control mb-2" placeholder="Type your comment..." name="comment" value={this.state.comment} onChange={this.onChange}></textarea>
                                <button className='btn btn-primary' id='commentBtn' onClick={this.onCommentClick}> Comment</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

UpdateProjectTask.propTypes = {
    getProjectTask: PropTypes.func.isRequired,
    project_task: PropTypes.object.isRequired,
    getProjectUsers: PropTypes.func.isRequired,
    updateProjectTask: PropTypes.func.isRequired,
    addCommentToProjectTask: PropTypes.func.isRequired,
    project: PropTypes.object.isRequired,
    errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    project_task: state.backlog.project_task,
    comments: state.backlog.comments,
    project: state.project,
    errors: state.errors
});


export default connect(mapStateToProps, { getProjectTask, updateProjectTask, addCommentToProjectTask, getProjectUsers })(UpdateProjectTask);