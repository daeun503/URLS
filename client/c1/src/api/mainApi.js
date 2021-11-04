import axios from 'axios';
import {listUrl} from './list_url';

axios.defaults.baseURL = listUrl.baeURL;

export default {
  getServerStatus() {
    return axios.get('/user');
  },

  getRelease() {
    console.log(this);
    try {
      const data = axios({
        url: listUrl.release_url,
        datatype: 'json',
        method: 'get',
      });
      return data;
    } catch (e) {
      return e;
    }
  },
};
