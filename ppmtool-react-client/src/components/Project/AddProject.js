import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { createProject, getProjectUsers } from '../../actions/projectActions';
import classnames from 'classnames';
import ErrorMessage from '../ErrorMessage';
import { getAllUsers } from '../../actions/userActions';

class AddProject extends Component {
    constructor() {
        super()

        this.state = {
            projectName: "",
            projectIdentifier: "",
            description: "",
            start_date: "",
            end_date: "",
            users: [],
            errors: {}
        }

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
    }

    componentDidMount(){
        this.props.getAllUsers();
    }
    //life cycle hooks
    componentWillReceiveProps(nextProps) {
        if (nextProps.errors) {
            this.setState({ errors: nextProps.errors });
        }
    }

    onChange(e) {
        this.setState(
            { [e.target.name]: e.target.value }
        )
    }

    handleSelectChange (event) {
        console.log(event.target.selectedOptions)
        const selectedValues = Array.from(event.target.selectedOptions, (option) => option.value);
        console.log(selectedValues);
        this.setState({[event.target.name] : selectedValues})
    };

    onSubmit(e) {
        e.preventDefault();
        const newProject = {
            "projectName": this.state.projectName,
            "projectIdentifier": this.state.projectIdentifier,
            "description": this.state.description,
            "start_date": this.state.start_date,
            "end_date": this.state.end_date,
            "listOfUsers": this.state.users
        }
        console.log(newProject);
        this.props.createProject(newProject, this.props.history)       
    }

    render() {
        const { errors } = this.state;
        const { user }  = this.props;
        const { project } = this.props;

        return (
            <div>
                <div className="project">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-8 m-auto">
                                <h5 className="display-4 text-center">Create New Project </h5>
                                <hr />
                                <form onSubmit={this.onSubmit}>

                                    <div className="form-group">
                                        <input type="text" className={classnames("form-control form-control-lg", {
                                            "is-invalid": errors.projectName
                                        })} placeholder="Project Name" name="projectName" value={this.state.projectName} onChange={this.onChange} />
                                        {errors.projectName && (
                                            <div className="invalid-feedback">{errors.projectName}</div>
                                        )}
                                    </div>

                                    <div className="form-group">
                                        <input type="text" className={classnames("form-control form-control-lg", {
                                            "is-invalid": errors.projectIdentifier
                                        })} placeholder="Unique Project ID" name="projectIdentifier" value={this.state.projectIdentifier} onChange={this.onChange} />
                                        {errors.projectIdentifier && (
                                            <div className="invalid-feedback">{errors.projectIdentifier}</div>
                                        )}
                                    </div>

                                    <div className="form-group">
                                        <textarea className={classnames("form-control form-control-lg", {
                                            "is-invalid": errors.description
                                        })} placeholder="Project Description" name="description" value={this.state.description} onChange={this.onChange} />
                                        {errors.description && (
                                            <div className="invalid-feedback">{errors.description}</div>
                                        )}
                                    </div>

                                    <h6>Start Date</h6>
                                    <div className="form-group">
                                        <input type="date" className="form-control form-control-lg" name="start_date" value={this.state.start_date} onChange={this.onChange} />
                                    </div>
                                    <h6>Estimated End Date</h6>
                                    <div className="form-group">
                                        <input type="date" className="form-control form-control-lg" name="end_date" value={this.state.end_date} onChange={this.onChange} />
                                    </div>

                                    <div className="form-group">
                                        <label htmlFor="selectUsers">Add users to the project(ctrl + click to select multiple)</label>
                                        <select multiple className="form-control" id="selectUsers" name="users" value={this.state.users} onChange={this.handleSelectChange}>
                                            {user.usernames.map((user) => (
                                                <option key={user} value={user}>
                                                    {user}
                                                </option>
                                            ))}
                                        </select>
                                    </div>

                                    <input type="submit" className="btn btn-primary btn-block mt-4" />

                                    {this.state.errors && <ErrorMessage message={this.state.errors.message}/>}
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

AddProject.propTypes = {
    createProject: PropTypes.func.isRequired,
    getAllUsers: PropTypes.func.isRequired,
    getProjectUsers: PropTypes.func.isRequired,
    user: PropTypes.object.isRequired,
    project: PropTypes.object.isRequired,
    errors: PropTypes.object.isRequired
}

const mapStateToProps = state => ({
    user: state.user,
    project: state.project,
    errors: state.errors
})

export default connect(mapStateToProps, { createProject, getAllUsers, getProjectUsers })(AddProject);
