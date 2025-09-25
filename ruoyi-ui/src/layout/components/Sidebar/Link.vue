<template>
  <component :is="type" v-bind="linkProps(to)">
    <slot />
  </component>
</template>

<script>
import { isExternal } from '@/utils/validate'

//需要在新标签页打开的路由
const blankList = ['/sales-dashboard']

export default {
  props: {
    to: {
      type: [String, Object],
      required: true
    }
  },
  computed: {
    isExternal() {
      return isExternal(this.to)
    },
    type() {
      if (this.isExternal) {
        return 'a'
      }
      return 'router-link'
    },
    isNewPage() {
      return blankList.some(item => {
        return item === this.to
      })
    }
  },
  methods: {
    linkProps(to) {
      if (this.isExternal) {
        return {
          href: to,
          target: '_blank',
          rel: 'noopener'
        }
      }
      if (this.isNewPage) {
        return {
          to: to,
          target: '_blank'
        }
      }
      return {
        to: to
      }
    }
  }
}
</script>
