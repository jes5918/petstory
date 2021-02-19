import axios from 'axios';
import React, { useState, useEffect } from 'react';
import { withRouter, useLocation } from 'react-router-dom';
import SearchResult from '../../components/Search/SearchResult';

// CSS
import styles from './SearchPage.module.css';

function SearchPage(props) {
  const location = useLocation();
  const keyword = unescape(decodeURIComponent(location.search.split('=')[1]));
  const [feedItems, setFeedItems] = useState([]);

  const getBoardByHashtagname = (e) => {
    const profileID = localStorage.getItem('profileId');
    axios({
      method: 'GET',
      url: `/api/board/findHashtag/${profileID}`,
      params: { hashtag_name: keyword },
    })
      .then((res) => {
        setFeedItems(res.data.data);
      })
      .catch((err) => {
        console.error(err);
      });
  };

  useEffect(() => {
    getBoardByHashtagname();
  }, [keyword]);

  return (
    <div className={styles.frame}>
      <h1 className={styles.title}>{`검색하신 "${keyword}"의 결과 입니다.`}</h1>
      <SearchResult items={feedItems} />
    </div>
  );
}

export default withRouter(SearchPage);
