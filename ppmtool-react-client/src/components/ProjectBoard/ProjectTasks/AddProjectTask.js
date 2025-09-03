import React, { Component } from 'react'
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import classnames from 'classnames';
import { addProjectTask } from '../../../actions/backlogActions';
import { getProjectUsers } from '../../../actions/projectActions';
import PropTypes from 'prop-types';

class AddProjectTask extends Component {

    constructor(props) {
        super(props)
        const { id } = this.props.match.params;
        console.log(this.props)

        this.state = {
            summary: "",
            acceptanceCriteria: "",
            status: "",
            priority: 0,
            dueDate: "",
            projectIdentifier: id,
            assignTo: "",
            projectUsers:[],
            errors: {}
        };
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        console.log(nextProps)
        if (nextProps.errors) {
            this.setState({ errors: nextProps.errors });
        }
        if(nextProps.project.usernames){
            this.setState({projectUsers: nextProps.project.usernames});
        }
    }

    componentDidMount(){
        this.props.getProjectUsers(this.props.match.params.id)
    }

    onChange(e) {
        console.log(e.target.name)
        console.log(e.target.value)
        this.setState({ [e.target.name]: e.target.value })
    }

    onSubmit(e) {
        e.preventDefault();

        const newTask = {
            summary: this.state.summary,
            acceptanceCriteria: this.state.acceptanceCriteria,
            status: this.state.status,
            priority: this.state.priority,
            dueDate: this.state.dueDate,
            username: this.state.assignTo
        };

        console.log(newTask);
        this.props.addProjectTask(this.state.projectIdentifier, newTask, this.props.history);
    }

    render() {
        const { id } = this.props.match.params;
        const { errors } = this.state;

        return (
            <div className="add-PBI">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <Link to={`/projectBoard/${id}`} className="btn btn-light">
                                Back to Project Board
                            </Link>
                            <h4 className="display-4 text-center">Add Project Task</h4>
                            <p className="lead text-center">Project Name + Project Code</p>
                            <form onSubmit={this.onSubmit}>
                                <div className="form-group">
                                    <input type="text" className={classnames("form-control form-control-lg", {
                                        "is-invalid": errors.summary
                                    })} name="summary" placeholder="Project Task summary" value={this.state.summary} onChange={this.onChange} />
                                    {errors.summary && (
                                        <div className="invalid-feedback">{errors.summary}</div>
                                    )}
                                </div>
                                <div className="form-group">
                                    <textarea className={classnames("form-control form-control-lg", {
                                        "is-invalid": errors.acceptanceCriteria
                                    })} placeholder="Acceptance Criteria" name="acceptanceCriteria" value={this.state.acceptanceCriteria} onChange={this.onChange}></textarea>
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
                                    <select className="form-control form-control-lg" name="assignTo" value={this.state.assignTo} onChange={this.onChange}>
                                        <option value="">Assign To</option>
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
                    </div>
                </div>
            </div >

        )
    }
}

AddProjectTask.propTypes = {
    addProjectTask: PropTypes.func.isRequired,
    getProjectUsers: PropTypes.func.isRequired,
    project: PropTypes.object.isRequired,
    errors: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    errors: state.errors,
    project: state.project
})

export default connect(mapStateToProps, { addProjectTask, getProjectUsers })(AddProjectTask);