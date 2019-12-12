import config from 'config';
import { authHeader, encrypt } from '../_helpers';
import { saveAs } from 'file-saver';
import { download } from './tools/download';


export const userService = {
    login,
    register,
    transfer,
    search,
    pay

};

function login(pemFile){
    console.log(pemFile);
    const requestOptions = {
        method: 'POST',
        body: pemFile
    };
    return fetch(`${config.apiUrl}/user/login`, { credentials: 'include', ...requestOptions }).then(handleResponse);
}

function register(param){
    console.log(param);
    const requestOptions = {
        method: 'GET'
    };
    return fetch(`${config.apiUrl}/user/register?${param}`, { credentials: 'include', ...requestOptions }).then(handleResponse);
}

function transfer(parameter) {
    
    const requestOptions = {
        method: 'GET',
        headers: authHeader()
    };
    return fetch(`${config.apiUrl}/user/transfer?${parameter}`, { credentials: 'include', ...requestOptions }).then(handleResponse);
}

function search(parameter) {
    
    const requestOptions = {
        method: 'GET',
        headers: authHeader()
    };
    return fetch(`${config.apiUrl}/user/search?${parameter}`, { credentials: 'include', ...requestOptions }).then(handleResponse);
}

function pay() {
    
    const requestOptions = {
        method: 'GET',
        headers: authHeader()
    };
    return fetch(`${config.apiUrl}/user/pay`, { credentials: 'include', ...requestOptions }).then(handleResponse);
}



function handleResponse(response) {
    return response.text().then(text => {
        const data = text && JSON.parse(text);
        console.log(data);
        if (!response.ok) {
            if (response.status === 401) {
                // auto logout if 401 response returned from api
                //logout();
            }
            const error = (data && data.message) || response.statusText;
            return Promise.reject(error);
        }

        return data;
    });
}

