import React, { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import { boardFindAll } from '../../_actions/boardAction';

// components
import FeedColumn from './FeedColumn';

// utils
import splitArray from '../../assets/js/SplitArray';
import styles from './FeedFrame.module.css';

// css
import './FeedFrame.css';

// 임시
import makeDummyData from './makeDummyData';

function FeedFrame(props) {
  // State
  const [allItems, setAllItems] = useState(splitArray(makeDummyData(20)));
  const dispatch = useDispatch();

  // 비동기 요청
  const axiosBoard = () => {
    dispatch(boardFindAll()).then((res) => {
      console.log('------------------------');
      console.log(res);
      console.log(res.data);
      console.log('------------------------');
      setAllItems(splitArray(res.data));
    });
  };

  // 임시 함수
  function callBackBoardAPI() {
    axiosBoard();
  }

  return (
    <div className={styles.frame}>
      {allItems.map((item) => (
        <FeedColumn item={item.items} key={item.list_id} />
      ))}
      <button type="button" onClick={callBackBoardAPI}>
        임시 추가
      </button>
    </div>
  );
}

export default FeedFrame;
