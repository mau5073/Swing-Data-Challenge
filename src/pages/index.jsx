import React from 'react'
import {Link} from "react-router-dom";


import styled, { createGlobalStyle, css, button} from 'styled-components';


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

const Button = styled.button `
    background-color: Green;
    color: white;
    padding: 10px 15px;
    border-radius: 10px;
`;

const MainPage = () => {
    
    return(
        <>
        <GlobalStyle/>
            <h3 className = "text-center title"> <u> Welcome to the Data Swing Challenge </u></h3>
            
            <h1 className = "text-center">
                <Link to="/Data"> <Button> Data Set </Button> </Link> 
                <h2>
                <Link to="/searchContinuityAboveValue"> <Button> SearchContinuityAboveValue </Button> </Link> 
                </h2>
                <h2>
                <Link to="/backSearchContinuityWithinRange"> <Button> BackSearchContinuityWithinRange </Button> </Link> 
                </h2>
                <h2>
                <Link to="/SearchContinuityAboveValueTwoSignals"> <Button> SearchContinuityAboveValueTwoSignals </Button> </Link> 
                </h2>
                <h2>
                <Link to="/SearchMultiContinuityWithinRange"> <Button> SearchMultiContinuityWithinRange </Button> </Link> 
                </h2>
            </h1>
        </>
    );
}

export default MainPage;