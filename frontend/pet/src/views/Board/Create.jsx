import React, { Component } from 'react';
import axios from 'axios';
import {COUNTRIES} from './countries';

const suggestions = COUNTRIES.map((country) => ({
    id: country,
    text: country,
  }));
export default class Create extends Component {
    fileObj = [];
    fileArray = [];
    images = [];
    titleRef = React.createRef();
    contextRef = React.createRef();
    constructor(props) {
        super(props);
        this.state = {
            items: [null],
            focused: false,
            input: '',
            file: [null],
            suggestions,
        };
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleInputKeyDown = this.handleInputKeyDown.bind(this);
        this.handleRemoveItem = this.handleRemoveItem.bind(this);
        this.uploadMultipleFiles = this.uploadMultipleFiles.bind(this);
        this.uploadFiles = this.uploadFiles.bind(this);
    }
    uploadMultipleFiles(e) {
        this.images = e.target.files;
        this.fileObj = e.target.files;
        for (let i = 0; i < this.fileObj.length; i++) {
            this.fileArray = [
            ...this.fileArray,
            {URL: URL.createObjectURL(this.fileObj[i]), id: i + Date.now(), Obj: this.fileObj[i]},
        ];
        }
        this.setState({img: this.tileObj});
        console.log(this.fileArray);
        this.setState({ file: this.fileArray});
    }

    uploadFiles(e) {
        e.preventDefault();
        const formData = new FormData();
        formData.append('title', this.titleRef.current.value);
        formData.append('context', this.contextRef.current.value);
        formData.append('hashtag', this.state.hashtag);
        console.log(this.fileArray);
        for (const i of this.fileArray) {
        const img = i.Obj;
          formData.append('files', img);
        }
        // console.log(this.state.file);
        axios.post("/api/board/create", formData, {})
        .then(response => console.log(response))
        .then(result => console.log(result))
        .catch(error => console.error('error', error));
        this.titleRef.current.value = '';
        this.contextRef.current.value = '';
    }
    handleDelete(image) {
      console.log(this.fileArray);
      const fileArray = this.fileArray.filter(item =>
      item.id !== image.id);
      this.fileArray = fileArray;
      this.setState({file: fileArray});
    }
    handleInputChange(evt) {
      this.setState({ input: evt.target.value });
    }

    handleInputKeyDown(evt) {
      if (evt.keyCode === 13) {
        const {value} = evt.target;
        evt.preventDefault();

        this.setState(state => ({
          items: [...state.items, value],
          input: '',
        }));
      }

      if (this.state.items.length && evt.keyCode === 8 && !this.state.input.length) {
        this.setState(state => ({
          items: state.items.slice(0, state.items.length - 1),
        }));
      }
    }
    handleRemoveItem(index) {
      return () => {
        this.setState(state => ({
          items: state.items.filter((item, i) => i !== index),
        }));
      };
    }
    render() {
        return (
            <form onSubmit={this.uploadFiles}>
                <div className="form-group multi-preview">
                    {(this.fileArray || {}).map(item => (
                      <div item={item} key={item.id}>
                        <img src={item.URL} key={item.id} width="100px" height="100px" alt="..." />
                        <span onClick={() => this.handleDelete(item)}>삭제</span>
                      </div>
                    ))}

                </div>

                <div className="form-group">
                    <input type="file" className="form-control" onChange={this.uploadMultipleFiles} multiple />
                        <input ref={this.titleRef} placeholder="제목을 입력하시오" type="text"/>
                        <input ref={this.contextRef} placeholder="내용을 입력하시오" type="text"/>

                </div>
                <ul>
                    {(this.state.items || []).map((item, i) =>
                      <li key={i} onClick={this.handleRemoveItem(i)}>
                        {item}
                      </li>,
                    )}
                    <input
                      width="200px"
                      value={this.state.input}
                      suggestions={this.suggestions}
                      onChange={this.handleInputChange}
                      placeholder="태그"
                      onKeyDown={this.handleInputKeyDown} />
                    </ul>
              <button>Upload</button>
            </form >
        );
    }
}
