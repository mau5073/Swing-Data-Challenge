import React from 'react'
import {Link} from "react-router-dom";
import DataComponent from '../components/DataComponent';






const Data = () => {

    return(
        <div className="data">
            <div className="topnav">
                    <Link to="/MainPage"  className="active"  href="#home"> Home
                    </Link>
                    <Link to="/searchContinuityAboveValue" className="active"  href="#SearchContinuityAboveValue">  SearchContinuityAboveValue
                    </Link> 
                    <Link to="/BackSearchContinuityWithinRange" className="active"  href="#BackSearchContinuityWithinRange">  BackSearchContinuityWithinRange
                    </Link> 
                    <Link to="/SearchContinuityAboveValueTwoSignals" className="active"  href="#SearchContinuityAboveValueTwoSignals">  SearchContinuityAboveValueTwoSignals
                    </Link> 
                    <Link to="/SearchMultiContinuityWithinRange" className="active"  href="#SearchMultiContinuityWithinRange">  SearchMultiContinuityWithinRange
                </Link> 
            </div>
            <DataComponent />
        </div>
  );
}

export default Data;