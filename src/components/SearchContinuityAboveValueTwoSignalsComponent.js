import React, {Component } from 'react';
import  '../style/SC.css';

export default class SearchContinuityAboveValueTwoSignalsComponent extends React.Component {
  state = {
      data1: "",
      data2: "",
      indexBegin: "",
      indexEnd: "",
      threshold1: "",
      threshold2: "",
      winLength: ""
    };
  
    change = e => {
      this.setState({
        [e.target.name]: e.target.value
      });
    };
  
    onSubmit = e => {
      e.preventDefault();
      this.props.onSubmit(this.state)
    };
  
    render() {
      return (
      <>
        <div className = "StyledFormWrapper">
      
         <div className = "StyledForm" >
          <h2 style = {{color:'White'}}>Search Parameters </h2>
              <label className= "para" >data1</label>
              <input className = "StyledInput sharedStyles" 
                name="data1"
                value={this.state.data1}
                onChange={e => this.change(e)}
              />
              <br />
              <label className= "para" >data2</label>
              <input className = "StyledInput sharedStyles" 
                name="data2"
                value={this.state.data2}
                onChange={e => this.change(e)}
              />
              <br />
              <label className= "para">  indexBegin</label>
            
              <input className = "StyledInput sharedStyles" 
                label ="indexBegin"
                name="indexBegin"
                placeholder=""
                value={this.state.indexBegin}
                onChange={e => this.change(e)}
              />
              <br />
              <label className= "para" >  indexEnd</label>
              <input className = "StyledInput sharedStyles" 
                name="indexEnd"
                placeholder=""
                value={this.state.indexEnd}
                onChange={e => this.change(e)}
              />
              <br />
              <label className= "para">  threshold1</label>
              <input className = "StyledInput sharedStyles" 
                name="threshold1"
                placeholder=""
                value={this.state.threshold1}
                onChange={e => this.change(e)}
              />
              <br />
              <label className= "para">  threshold2</label>
              <input className = "StyledInput sharedStyles" 
                name="threshold2"
                placeholder=""
                value={this.state.threshold2}
                onChange={e => this.change(e)}
              />
              <br />
              <label className= "para">  winLength</label>
              <input className = "StyledInput sharedStyles" 
                name="winLength"
                placeholder=""
                value={this.state.winLength}
                onChange={e => this.change(e)}
              />
              <br />
              
              <button className="button StyledInput sharedStyles" onClick={e => this.onSubmit(e)}>Submit</button >
              <br />
          </div>
       
        </div>   
       
        </>

      );
      
    }
  }
  