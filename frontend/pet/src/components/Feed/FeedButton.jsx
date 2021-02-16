import { useState, useRef } from 'react';

// Components
import FeedSaveContainer from './FeedSaveContainer';
import { Menu, MenuItem, Button, Box } from '@material-ui/core';
import { makeStyles } from '@material-ui/styles';
import { request } from '../../utils/axios';

const useStyles = makeStyles((theme) => ({
  root: {
    backgroundColor: '#fafafa',
  },
  button: {
    // 기본 버튼 스타일
    '&, &:hover': {
      outline: 0,
      border: 0,
    },
  },
  right: {
    // 우측 버튼 - 저장
    color: 'white',
    backgroundColor: '#ff1744',
    borderRadius: '0 10px 10px 0',
    paddingRight: '30px',
    paddingLeft: '30px',
    '&:hover': {
      backgroundColor: '#c4001d',
    },
  },
  left: {
    // 좌측 버튼 - 저장소 선택
    backgroundColor: '#fafafa',
    borderRadius: '10px 0 0 10px',
    paddingRight: '30px',
    paddingLeft: '30px',
    '&:hover': {
      backgroundColor: '#c2c2c2',
    },
  },
  frame: {
    // 버튼 요소를 담고 있는 프레임.
    position: 'absolute',
    display: 'flex',
    alignItems: 'center',
    top: 10,
    left: '50%',
    transform: 'translateX(-50%)',
    zIndex: 1,
    width: 'fit-content',
    '&:hover .button': {
      display: 'inline-block',
    },
  },
}));

const options = ['Cats', 'Dogs', 'My Collection'];

function FeedButton(props) {
  // State
  const [anchorEl, setAnchorEl] = useState(null);
  const [selectedIndex, setSelectedIndex] = useState(0);

  // Data
  const memberId = props.memberId;
  const boardId = props.boardId;
  const postList = props.postList;

  // 커스텀 스타일
  const classes = useStyles();

  // Methods
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleSave = async () => {
    const memberPostlistId = props.postList[selectedIndex].memberPostlistId;

    const data = {
      memberId,
      boardId,
      memberPostlistId,
    };

    // 피드 세이브 요청
    request('GET', '/api/postlist/add', data);
  };

  const handleMenuItemClick = (event, index) => {
    setSelectedIndex(index);
    setAnchorEl(null);
  };

  return (
    <Box className={`${classes.frame} active`}>
      {/* 피드 저장소 리스트 버튼 */}
      <Button
        aria-controls="simple-menu"
        aria-haspopup="true"
        onClick={handleClick}
        className={`${classes.button} ${classes.left}`}
      >
        <FeedSaveContainer
          text={
            postList[selectedIndex]
              ? postList[selectedIndex].postlistName
              : '저장 위치 선택'
          }
        ></FeedSaveContainer>
      </Button>
      <Menu
        className="menu__frame"
        id="simple-menu"
        anchorEl={anchorEl}
        getContentAnchorEl={null}
        anchorOrigin={{
          vertical: 'middle',
          horizontal: 'center',
        }}
        transformOrigin={{
          vertical: 'top',
          horizontal: 'center',
        }}
        keepMounted
        open={Boolean(anchorEl)}
        onClose={handleClose}
      >
        {postList.map((post, index) => (
          <MenuItem
            key={post.memberPostlistId}
            selected={index === selectedIndex}
            onClick={(event) => handleMenuItemClick(event, index)}
          >
            {post.postlistName}
          </MenuItem>
        ))}
      </Menu>

      {/* 피드 저장 버튼 */}
      <Button
        className={`${classes.button} ${classes.right}`}
        onClick={handleSave}
      >
        <span>저장</span>
      </Button>
    </Box>
  );
}

export default FeedButton;
