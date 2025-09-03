import React, { Component } from 'react'
import { getProject, createProject } from '../../actions/projectActions';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import classnames from 'classnames';
import { getAllUsers } from '../../actions/userActions';
import { getProjectUsers } from '../../actions/projectActions';
import ErrorMessage from '../ErrorMessage';

class UpdateProject extends Component {
    //set state
    constructor() {
        super()

        this.state = {
            id: "",
            projectName: "",
            projectIdentifier: "",
            description: "",
            start_date: "",
            end_date: "",
            usernames:[],
            errors: {}
        }
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.handleSelectChange = this.handleSelectChange.bind(this);
        this.displayUsers = this.displayUsers.bind(this);
    }

    handleSelectChange (event) {
        const selectedValues = Array.from(event.target.selectedOptions, (option) => option.value);
        this.setState({[event.target.name] : selectedValues})
    };

    componentWillReceiveProps(nextProps) {

        if (nextProps.errors) {
            this.setState({ errors: nextProps.errors });
        }

        console.log(nextProps.project);
        const {
            id,
            projectName,
            projectIdentifier,
            description,
            start_date,
            end_date,
            usernames
        } = nextProps.project.project

        this.setState({
            id,
            projectName,
            projectIdentifier,
            description,
            start_date,
            end_date,
            usernames
        });
    }


    componentDidMount() {
        const { id } = this.props.match.params;
        this.props.getProject(id, this.props.history);
        this.props.getAllUsers();
        this.props.getProjectUsers(id);
    }

    displayUsers(user){
        const { project } = this.props;
        const projectUsers = project.usernames;
        if(projectUsers && projectUsers.length>0){
            if(projectUsers.includes(user)){
                return <option key={user} value={user} selected>{user}</option>
            }
        }
        return <option key={user} value={user}>{user}</option>

    }

    onChange(e) {
        this.setState({ [e.target.name]: e.target.value });
    }

    onSubmit(e) {
        e.preventDefault()

        const updateProject = {
            id: this.state.id,
            projectName: this.state.projectName,
            projectIdentifier: this.state.projectIdentifier,
            description: this.state.description,
            start_date: this.state.start_date,
            end_date: this.state.end_date,
            users: this.state.usernames
        };
        console.log(updateProject);

        this.props.createProject(updateProject, this.props.history);
    }

    render() {

        const { errors } = this.state;
        const { user }  = this.props;
        console.log(user)
        

        return (
            <div className="project">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <h5 className="display-4 text-center">Update Project form</h5>
                            <hr />
                            <form onSubmit={this.onSubmit}>
                                <div className="form-group">
                                    <input type="text" className={classnames("form-control form-control-lg", {
                                        "is-invalid": errors.projectName
                                    })} name="projectName" placeholder="Project Name" value={this.state.projectName} onChange={this.onChange} />
                                    {
                                        errors.projectName && (
                                            <div className="invalid-feedback">{errors.projectName}</div>
                                        )
                                    }
                                </div>
                                <div className="form-group">
                                    <input type="text" className="form-control form-control-lg" name="projectIdentifier" placeholder="Unique Project ID" value={this.state.projectIdentifier} onChange={this.onChange}
                                        disabled />
                                </div>
                                <div className="form-group">
                                    <textarea className={classnames("form-control form-control-lg", {
                                        "is-invalid": errors.description
                                    })} name="description" placeholder="Project Description" value={this.state.description} onChange={this.onChange} />
                                    {
                                        errors.description && (
                                            <div className="invalid-feedback">{errors.description}</div>
                                        )
                                    }
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
                                    <label htmlFor="selectUsers">Edit users of the project(ctrl + click to select multiple)</label>
                                    <select multiple className="form-control" id="selectUsers" name="usernames" value={this.state.usernames} onChange={this.handleSelectChange}>
                                        {user.usernames.map(this.displayUsers)}
                                    </select>
                                </div>

                                <input type="submit" className="btn btn-primary btn-block mt-4" />
                                {this.state.errors && <ErrorMessage message={this.state.errors.message}/>}
                            </form>
                        </div>
                    </div>
                </div>
            </div>

        )
    }
}

UpdateProject.propTypes = {
    getProject: PropTypes.func.isRequired,
    createProject: PropTypes.func.isRequired,
    project: PropTypes.object.isRequired,
    getAllUsers: PropTypes.func.isRequired,
    getProjectUsers: PropTypes.func.isRequired,
    user: PropTypes.object.isRequired,
    errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    user: state.user,
    project: state.project,
    errors: state.errors
});

export default connect(mapStateToProps, { getProject, createProject, getAllUsers, getProjectUsers })(UpdateProject);