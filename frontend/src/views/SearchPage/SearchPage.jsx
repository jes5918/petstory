import React from 'react';
import { withRouter, useLocation } from 'react-router-dom';
import PostListResult from '../../components/Feed/PostListResult';

// CSS
import styles from './SearchPage.module.css';

function SearchPage(props) {
  const location = useLocation();
  const keywored = location.search.split('=')[1];

  return (
    <div className={styles.frame}>
      <h1
        className={styles.title}
      >{`검색하신 "${keywored}"의 결과 입니다.`}</h1>
    </div>
  );
}

export default withRouter(SearchPage);
