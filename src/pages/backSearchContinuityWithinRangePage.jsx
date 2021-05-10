import React, { Component } from 'react'
import {Link} from "react-router-dom";
import BackSearchComponent from '../components/BackSearchComponent';
import axios from 'axios'
import styled, { createGlobalStyle, css } from 'styled-components';


const GlobalStyle = createGlobalStyle`
  html {
    height: 100%
  }
  body {
    font-family: Arial, Helvetica, sans-serif;
    background: linear-gradient(to bottom, #ececec, #434442);
    height: 100%;
    margin: 0;
    color: #555;
  }
`;


class BackSearchContinuityWithinRange extends Component {
    state = {
        fields: {data: "",
        indexBegin: "",
        indexEnd: "",
        thresholdLo: "",
        thresholdHi: "",
        winLength: ""},  
        result : {}
      };
     
   


    onSubmit =  async fields => {
        this.setState({fields});  
        console.log(this.state.fields.data);
        
        await axios.get("http://localhost:8080/backSearchContinuityWithinRange", { params: 
            { data: parseInt(fields.data),
              indexBegin: parseInt(fields.indexBegin),
              indexEnd: parseInt(fields.indexEnd),
              thresholdLo: parseFloat(fields.thresholdLo),
              thresholdHi: parseFloat(fields.thresholdHi),
              winLength: parseInt(fields.winLength),
            }
         }).then((response) => {
             this.setState({result: response.data}) });
        }
    render(){  
      
    return(
        
    <>
    < GlobalStyle/>
            <div className="topnav">
                <Link to="/MainPage"  className="active"  href="#home">
                       Home
                </Link>
                <Link to="/Data" className="active"  href="#DATA">  DATA SET  
                </Link> 
                <Link to="/searchContinuityAboveValue" className="active"  href="#SearchContinuityAboveValue">  SearchContinuityAboveValue
                    </Link> 
                <Link to="/SearchContinuityAboveValueTwoSignals" className="active"  href="#SearchContinuityAboveValueTwoSignals">  SearchContinuityAboveValueTwoSignals
                </Link> 
                <Link to="/SearchMultiContinuityWithinRange" className="active"  href="#SearchMultiContinuityWithinRange">  SearchMultiContinuityWithinRange
                </Link> 
                
            </div>
            <h2 className = "title">BackSearchContinuityWithinRange</h2>
            <div class="column">
                <BackSearchComponent onSubmit={fields => this.onSubmit(fields)}/>

            </div>
            <div  className="column">
                <h2 className = "group">
               
                    <h2 style = {{color: "White" }} >Results </h2>
                    
                    <label> Index: </label > <pre> {JSON.stringify(this.state.result, null, 2)} </pre>
                    
                </h2>
            </div>
          
    </>
  
  );
}
}

export default BackSearchContinuityWithinRange;