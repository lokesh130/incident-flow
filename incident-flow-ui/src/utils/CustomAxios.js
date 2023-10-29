import axios from 'axios';
import qs from 'qs';
import {Config} from '../config/config';

const instance = axios.create({
  // Uncomment following while doing locat testing
  baseURL: Config.baseUrl,   
  withCredentials: true,
  paramsSerializer: (params) => {
    return qs.stringify(params, { arrayFormat: 'repeat' });
  },
});

export default instance;
