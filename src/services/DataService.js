import axios from 'axios'

const DATA_REST_API_URL = "http://localhost:8080/Data"

class DataService {
    getData(){
        return axios.get(DATA_REST_API_URL);
    
    }
}

export default new DataService();