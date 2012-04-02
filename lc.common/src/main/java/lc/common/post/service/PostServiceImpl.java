package lc.common.post.service;

import java.util.List;

import lc.common.post.domain.Post;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("postService")
public class PostServiceImpl implements PostService {
	private Logger logger = LogManager.getLogger(getClass());
	
	@Autowired(required=true)
	private PostMapper postMapper;
	
	public List<Post> listZip(Post post) throws Exception{
		return postMapper.listZip(post);
	}
}
