<template>
  <el-carousel :interval="4000" type="card" height="450px">
    <el-carousel-item v-for="card in state.cardList" :key="card">
      <div class="card">
        <img class="card-img" :src="card.img" alt="">
        <div class="d-flex justify-content-between align-items-center">
          <h2 class="ms-2 my-3">{{ card.location }}</h2>
          <a class="card-guide mt-2 me-3" :href="card.link">
            <i class="card-guide-icon fas fa-compass"></i>
            <p class="card-guide-text my-0">여행 가이드</p>
          </a>
        </div>
        <p class="mx-2 my-0">{{ card.description }}</p>
      </div>
    </el-carousel-item>
  </el-carousel>
</template>
<script>
import { reactive } from 'vue'

export default {
  setup() {
    const state = reactive ({
      cardList: [
        {
          img: require('@/assets/korea.jpg'),
          location: '대한민국',
          description: '불교 사찰, 고궁, 제주도, DMZ 수도인 서울로 유명한 동아시아 국가',
          link: 'https://www.google.com/travel/guide/compare?q=%EB%8C%80%ED%95%9C%EB%AF%BC%EA%B5%AD+%EC%97%AC%ED%96%89&sxsrf=ALeKk01JbEMG6dbsx5oxxs--z5xCVCTRjg:1629280844241&ei=TNocYdGeDoWxmAW18b-gBQ&oq=%EB%8C%80%ED%95%9C%EB%AF%BC%EA%B5%AD+%EC%97%AC%ED%96%89&gs_lcp=Cgdnd3Mtd2l6EAMyCggAEIAEEIcCEBQyBQgAEIAEMgoIABCABBCHAhAUMgUIABCABDIFCAAQgAQyBQgAEIAEMgUIABCABDIFCAAQgAQyBQgAEIAEMgUIABCABDoECCMQJzoLCAAQgAQQsQMQgwE6CAgAEIAEELEDOgQIABADOgcIABCxAxBDOgQIABBDSgQIQRgAUJQPWIUiYOoiaARwAngCgAGSAYgBmhCSAQQwLjE5mAEAoAEBwAEB&sclient=gws-wiz&uact=5&dest_mid=/m/06qd3&dest_src=o&sa=X&ved=2ahUKEwi265iSqLryAhXQGaYKHSpNBdoQ6tEBKAUwAHoECBIQDA',
        },    
        {
          img: require('@/assets/america.jpg'),
          location: '미국',
          description: '뉴욕 시, 할리우드, 그랜드 캐니언 등의 대규모 국립공원이 있는 국가',
          link: 'https://www.google.com/travel/guide/compare?q=%EB%AF%B8%EA%B5%AD+%EC%97%AC%ED%96%89&oq=%EB%AF%B8%EA%B5%AD+%EC%97%AC%ED%96%89&aqs=chrome..69i57j0i512j0i131i433i512l2j0i512j0i131i433i512j0i433i512j0i131i433i512l2j0i512.1502j0j15&sourceid=chrome&ie=UTF-8&dest_mid=/m/09c7w0&dest_src=o&sa=X&ved=2ahUKEwih0tbvp7ryAhWCF6YKHcO3BkwQ6tEBKAUwAHoECAwQDA',
        },    
        {
          img: require('@/assets/uk.jpg'),
          location: '영국',
          description: '런던, 에든버러, 카디프, 벨파스트와 중세시대 성, 스톤헨지, 하드리아누스 방벽이 있는 국가',
          link: 'https://www.google.com/travel/guide/compare?q=%EC%98%81%EA%B5%AD+%EC%97%AC%ED%96%89&sxsrf=ALeKk002iepn-C-qx3GsGy7ApIaaaUGGgQ:1629280849517&ei=UdocYfb-HtCzmAWqmpXQDQ&oq=%EC%98%81%EA%B5%AD+%EC%97%AC%ED%96%89&gs_lcp=Cgdnd3Mtd2l6EAMyCggAEIAEEIcCEBQyBAgAEEMyBAgAEEMyBQgAEIAEMgUIABCABDIFCAAQgAQyBQgAEIAEMgQIABBDMgUIABCABDIKCAAQgAQQhwIQFDoECCMQJzoICAAQgAQQsQM6CwgAEIAEELEDEIMBSgQIQRgAULkJWKITYIwUaAFwAHgBgAHLAYgB7gqSAQYwLjExLjGYAQCgAQHAAQE&sclient=gws-wiz&uact=5&dest_mid=/m/07ssc&dest_src=o&sa=X&ved=2ahUKEwjBm4bUqLryAhUlGaYKHf3ZA10Q6tEBKAUwAHoECAwQDA',
        },    
        {
          img: require('@/assets/africa.jpg'),
          location: '아프리카',
          description: '두 번째로 큰 대륙에 12억이 넘는 인구와 54개국이 존재하는 대륙',
          link: 'https://www.google.com/travel/guide/compare?q=%EC%95%84%ED%94%84%EB%A6%AC%EC%B9%B4+%EC%97%AC%ED%96%89&oq=%EC%95%84%ED%94%84%EB%A6%AC%EC%B9%B4+%EC%97%AC%ED%96%89&aqs=chrome..69i57j35i39l2j0i512l2j0i131i433i512j0i3j0i131i433i512l3.1302j0j15&sourceid=chrome&ie=UTF-8&dest_mid=/m/0dg3n1&dest_src=o&sa=X&ved=2ahUKEwi0oIbJqbryAhUWwosBHdOYBCgQ6tEBKAQwAHoECA8QCw',
        },    
        {
          img: require('@/assets/asia.jpg'),
          location: '아시아',
          description: '지구에서 가장 큰 대륙에 전 세계 인구의 약 60%가 살고, 다양한 언어와 민족이 공존하는 대륙',
          link: 'https://www.google.com/travel/guide/compare?q=%EC%95%84%EC%8B%9C%EC%95%84+%EC%97%AC%ED%96%89&sxsrf=ALeKk01leMmjExtOO5LOaVPbZPWfiE3_xQ:1629281576708&ei=KN0cYY3NKqavmAXRtYfgCg&oq=%EC%95%84%EC%8B%9C%EC%95%84+%EC%97%AC%ED%96%89&gs_lcp=Cgdnd3Mtd2l6EAMyCggAEIAEEIcCEBQyBQgAEIAEMgUIABCABDIFCAAQgAQyBQgAEIAEMgUIABCABDIKCAAQgAQQhwIQFDIFCAAQgAQyBQgAEIAEMgUIABCABDoECCMQJzoECAAQQzoECAAQAzoLCAAQgAQQsQMQgwE6CAgAEIAEELEDSgQIQRgAUK4tWMU1YKY2aANwAngCgAF0iAGLCpIBBDAuMTKYAQCgAQHAAQE&sclient=gws-wiz&uact=5&dest_mid=/m/0j0k&dest_src=o&sa=X&ved=2ahUKEwjG4pCUq7ryAhU1LqYKHT0JDLEQ6tEBKAQwAHoECBEQCw',
        },    
        {
          img: require('@/assets/southamerica.jpg'),
          location: '남아메리카',
          description: '큰 대륙에 비해 나라가 10개 조금 넘는 수준으로, 스케일이 크고 아름다운 대륙',
          link: 'https://www.google.com/travel/guide/compare?q=%EB%82%A8%EB%AF%B8+%EC%97%AC%ED%96%89&sxsrf=ALeKk01S9qWm5SvC_dwK-31DVVoHh3prig:1629281658885&ei=et0cYca9NbXcmAW9krCICw&oq=%EB%82%A8%EB%AF%B8+%EC%97%AC%ED%96%89&gs_lcp=Cgdnd3Mtd2l6EAMyCggAEIAEEIcCEBQyCggAEIAEEIcCEBQyBQgAEIAEMgQIABBDMgQIABBDMgUIABCABDIFCAAQgAQyBQgAEIAEMgUIABCABDIFCAAQgAQ6BAgjECc6CAgAEIAEELEDOgcIABCABBAKOgsIABCABBCxAxCDAToHCCMQ6gIQJzoECAAQA0oECEEYAFCKCViqHGCHHWgCcAB4AYABd4gB2wqSAQQwLjEymAEAoAEBsAEKwAEB&sclient=gws-wiz&uact=5&dest_mid=/m/06n3y&dest_src=o&sa=X&ved=2ahUKEwjjyNGRrLryAhUiG6YKHWP5B50Q6tEBKAQwAHoECAUQCw',
        },    
      ]
    })
    return { state }    
  },
}
</script>
<style>
.card {
  display: block;
  height: 450px;
  border: 1px solid lightgreen;
  /* border: none; */
  border-radius: 15px;
  opacity: 0.85;
}
.card-img {
  height: 70%;
  border-top-left-radius: 15px;
  border-top-right-radius: 15px;
}
.card-guide {
  text-align: center;
  text-decoration-line: none;
}
.card-guide:hover {
  color: green;
  cursor: pointer;
  font-weight: bold;
}
.card-guide-icon {
  font-size: 25px;
}
.card-guide-text {
  font-size: 10px;
}
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}
.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
  border-radius: 15px;
  border: none;
}
.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
  border-radius: 15px;
  border: none;
}
.el-carousel__indicators--outside {
  margin-top: 2%;
  margin-bottom: 2%;
}
</style>