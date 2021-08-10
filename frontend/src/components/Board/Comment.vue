<template>
  <div>
    {{ comment.userId }}
    {{ comment.comment }}
  </div>
</template>

<script>
export default {
  name: 'Comment',
  props: {
    comment: {
      type: Object,
    }
  },
  setup() {
  },
}
</script>