import axios from 'axios';

axios.defaults.baseURL = 'http://k5b201.p.ssafy.io:4000';

export default {
  getServerStatus() {
    return axios
      .get('/user')
      .then(res => res)
      .catch(error => error);
  },
};
