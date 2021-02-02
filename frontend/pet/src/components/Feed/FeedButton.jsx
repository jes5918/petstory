import React from 'react';
import styles from './FeedButton.module.css';

// fontawesome
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faAngleDown } from '@fortawesome/free-solid-svg-icons';

function FeedButton(props) {
  const isHover = props.isHover ? ` ${styles.frame__hover}` : '';
  return (
    <div className={`${styles.frame}${isHover}`}>
      <button className={`${styles.btn} ${styles.left}`} type="button">
        <span className={styles.text}>내 피드</span>
        <FontAwesomeIcon icon={faAngleDown} />
      </button>
      <button className={`${styles.btn} ${styles.right}`} type="button">
        저장
      </button>
    </div>
  );
}

export default FeedButton;
