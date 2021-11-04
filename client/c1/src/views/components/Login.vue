<template>
  <div id="option">
    <template v-if="this.getToken && this.getUsername"
      ><div>토큰이 있다.</div>
      <div>
        <p>{{ this.getToken }}</p>
        <p>{{ this.getUsername }}</p>
        <button @click="deleteToken">토큰 초기화</button>
      </div>
    </template>
    <template v-else
      ><div>토큰이 없다.</div>
      <div>
        <p>{{ this.getToken }}</p>
        <p>{{ this.getUsername }}</p>
        <button @click="newToken">토큰 생성</button>
      </div></template
    >
  </div>
</template>

<script>
import {mapGetters} from 'vuex';
import {mixin} from '../../utils/mixin';

export default {
  data() {
    return {
      username: null,
      token: null,
      saved: false,
    };
  },

  computed: {
    ...mapGetters(['getUsername', 'getToken']),
  },

  methods: {
    initComponent() {
      this.username = this.$store.getters.getUsername;
      this.token = this.$store.getters.getToken;
    },
    newToken() {
      const payload = {
        username: 'keelim',
        token: 'hello-token',
      };
      this.save(payload, 'updated');
    },
    deleteToken() {
      const payload = {
        username: null,
        token: null,
      };
      this.save(payload, 'updated');
    },
    signIn() {},
  },

  mixins: [mixin],
};
</script>

<style lang="scss" scoped>
#option {
  position: relative;
}
</style>
