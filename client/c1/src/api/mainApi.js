import axios from 'axios';
import {listUrl, samples} from './list_url';

axios.defaults.baseURL = listUrl.baeURL;

export default {
  getServerStatus() {
    return axios
      .get('/user')
      .then(res => res)
      .catch(error => error);
  },

  getRelease() {
    // return axios
    //   .get(listUrl.release_url)
    //   .then(res => res)
    //   .catch(error => error);

    // TODO
    return samples;
  },
  inject(url, token) {
    // TODO
    return axios
      .post('/inject', {
        url,
        token,
      })
      .then(res => res)
      .catch(error => error);
  },
};
