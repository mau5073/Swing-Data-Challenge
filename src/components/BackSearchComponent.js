import React, {Component } from 'react';
import  '../style/SC.css';

export default class BackSearchComponent extends React.Component {
  state = {
        data: "",
        indexBegin: "",
        indexEnd: "",
        thresholdLo: "",
        thresholdHi: "",
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
          <label className= "para" >Data</label>
              <input className = "StyledInput sharedStyles" 
                name="data"
                value={this.state.data}
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
              <label className= "para">  thresholdLo</label>
              <input className = "StyledInput sharedStyles" 
                name="thresholdLo"
                placeholder=""
                value={this.state.thresholdLo}
                onChange={e => this.change(e)}
              />
              <br />
              <label className= "para">  thresholdHi</label>
              <input className = "StyledInput sharedStyles" 
                name="thresholdHi"
                placeholder=""
                value={this.state.thresholdHi}
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
  