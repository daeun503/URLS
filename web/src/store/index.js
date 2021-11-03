import { store } from 'quasar/wrappers'
import { createStore } from 'vuex'
import moduleExample from './module-example'
import user from './user'

export default store(function (/* { ssrContext } */) {
  const Store = createStore({
    modules: {
      moduleExample,
      user
    },

    // enable strict mode (adds overhead!)
    // for dev mode and --debug builds only
    strict: process.env.DEBUGGING
  })

  return Store
})
