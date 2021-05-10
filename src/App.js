
import './App.css';
import {BrowserRouter as Router, Route, Switch, Link, Redirect} from "react-router-dom"

//pages  
import MainPage from "./pages";
import NoPage from "./pages/NoPage";
import DataPage from "./pages/DataPage";
import SearchContinuityAboveValuePage from "./pages/searchContinuityAboveValuePage";
import backSearchContinuityWithinRangePage from "./pages/backSearchContinuityWithinRangePage";
import SearchContinuityAboveValueTwoSignalsPage from "./pages/SearchContinuityAboveValueTwoSignalsPage";
import SearchMultiContinuityWithinRangePage from "./pages/SearchMultiContinuityWithinRangePage";

function App() {
  return (
    
      <Router>
        <Switch>
          
          <Route path ="/MainPage" component={MainPage} />
          <Route path ="/Data" component={DataPage} />
          <Route path ="/searchContinuityAboveValue" component={SearchContinuityAboveValuePage} />
          <Route path ="/backSearchContinuityWithinRange" component={backSearchContinuityWithinRangePage} />
          <Route path ="/SearchContinuityAboveValueTwoSignals" component={SearchContinuityAboveValueTwoSignalsPage} />
          <Route path ="/SearchMultiContinuityWithinRange" component={SearchMultiContinuityWithinRangePage} />
          <Route path ="/NoPage"component={NoPage} />
          <Route path ="/" component={MainPage} />
          <Redirect to = "/NoPage" />
        </Switch>
      </Router>
  
  );
}

export default App;
