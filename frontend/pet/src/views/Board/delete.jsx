import React, { Component } from 'react';
import axios from 'axios';

export default class Delete extends Component {
    delete=() => {
        axios.get(`board/delete/${this.props.obj._id}`)
            .then(console.log('Deleted'))
            .catch(err => console.log(err));
    }
    render() {
        return (
            <div>
                 <button onClick={this.delete} className="btn btn-danger">Delete</button>
            </div>
        );
    }
}

