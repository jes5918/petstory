import React, { Fragment } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faAngleDown } from '@fortawesome/free-solid-svg-icons';
import { makeStyles } from '@material-ui/core';

const useStyles = makeStyles({
  root: {
    whiteSpace: 'nowrap',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
    marginRight: 10,
    width: 80,
    textTransform: 'capitalize',
  },
});

function FeedSaveContainer(props) {
  const classes = useStyles();
  return (
    <>
      {/* span 부분 내용을 props.text로 대체 */}
      {/* <span className={classes.root}>Collection</span> */}
      {/* <span className={classes.root}>
        {props.text ? props.text : 'Collection'}
      </span> */}
      <span className={classes.root}>
        {props.text ? props.text : '목록을 만들어보세요!'}
      </span>
      <FontAwesomeIcon icon={faAngleDown} />
    </>
  );
}

export default FeedSaveContainer;
