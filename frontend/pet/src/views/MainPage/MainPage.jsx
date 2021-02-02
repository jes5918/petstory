import React, { useEffect, useState } from 'react';
import { withRouter } from 'react-router-dom';
import FeedDetail from '../../components/Feed/FeedDetail';
import FeedFrame from '../../components/Feed/FeedFrame';

function MainPage(props) {
  return (
    <div>
      {/* <FeedFrame /> */}
      <FeedDetail />
    </div>
  );
}

export default withRouter(MainPage);
