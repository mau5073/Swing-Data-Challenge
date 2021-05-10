import React, { Component } from 'react';
import DataService from '../services/DataService';

class DataComponent extends React.Component{

    constructor(props){
        super(props)
        this.state ={
            data:[]
        }
    }

    componentDidMount(){
        DataService.getData().then((Response) =>{
            this.setState({data: Response.data})
        });
    }

    render (){
        return (
            
            <div>
                
                <h1 className = "text-center"> Data</h1>
                <table className = "table table-striped table-responsive">
                    <thead style = {{backgroundColor: "Black",  color: "white", textAlign: "center" }}>
                        
                        <td> row </td>
                        <td> timestamp </td>
                        <td> ax </td>
                        <td> ay </td>
                        <td> az </td>
                        <td> wx </td>
                        <td> wy </td>
                        <td> wz </td>
                        
                    </thead>
                    <tbody className = "td">
                        {
                                   
                            this.state.data.map((data, index) => ( 
                            <tr key={data.id} className = "td" style = {{textAlign: "center"}}>
                                <td> {index}</td>
                                <td> {data[0]}</td>
                                <td> {data[1]}</td>
                                <td> {data[2]}</td>  
                                <td> {data[3]}</td>  
                                <td> {data[4]}</td>  
                                <td> {data[5]}</td>
                                <td> {data[6]}</td>          
                            </tr>
                            ))
                        }

                    </tbody>
                </table>
            </div>
        )
    }
}
export default DataComponent
